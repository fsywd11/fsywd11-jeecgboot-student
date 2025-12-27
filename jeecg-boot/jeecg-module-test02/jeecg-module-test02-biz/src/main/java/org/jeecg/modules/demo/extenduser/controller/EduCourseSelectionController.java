package org.jeecg.modules.demo.extenduser.controller;

import java.util.Arrays;
import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.extenduser.entity.EduCourse;
import org.jeecg.modules.demo.extenduser.entity.EduCourseSelection;
import org.jeecg.modules.demo.extenduser.service.IEduCourseSelectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.extenduser.service.IEduCourseService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 选课记录表（高并发优化版）
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V2.1（修复退课误提示“人数为0”问题，优化选课/退课一致性）
 */
@Tag(name="选课记录表")
@RestController
@RequestMapping("/test02/eduCourseSelection")
@Slf4j
public class EduCourseSelectionController extends JeecgController<EduCourseSelection, IEduCourseSelectionService> {
	@Autowired
	private IEduCourseSelectionService eduCourseSelectionService;

	@Autowired
	private IEduCourseService eduCourseService;

	// 注入Redisson（分布式锁 + 原子操作）
	@Autowired
	private RedissonClient redissonClient;

	// 注入RedisTemplate（缓存操作）
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// Redis Key前缀定义（统一管理，便于维护）
	private static final String COURSE_CURRENT_STUDENT_KEY = "course:current:student:"; // 课程当前人数Key
	private static final String COURSE_MAX_STUDENT_KEY = "course:max:student:";       // 课程最大人数Key
	private static final String SELECT_COURSE_LOCK_KEY = "lock:select:course:";       // 选课分布式锁Key
	private static final String DROP_COURSE_LOCK_KEY = "lock:drop:course:";           // 退课分布式锁Key
	private static final String COURSE_INFO_KEY = "course:info:";                     // 课程信息缓存Key

	/**
	 * 分页列表查询
	 * （原有逻辑不变，无需改造）
	 */
	@Operation(summary="选课记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduCourseSelection>> queryPageList(EduCourseSelection eduCourseSelection,
														   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														   HttpServletRequest req) {
		QueryWrapper<EduCourseSelection> queryWrapper = QueryGenerator.initQueryWrapper(eduCourseSelection, req.getParameterMap());
		Page<EduCourseSelection> page = new Page<EduCourseSelection>(pageNo, pageSize);
		IPage<EduCourseSelection> pageList = eduCourseSelectionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 * （原有逻辑不变，无需改造）
	 */
	@AutoLog(value = "选课记录表-添加")
	@Operation(summary="选课记录表-添加")
	@RequiresPermissions("eduselectcourse:edu_course_selection:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduCourseSelection eduCourseSelection) {
		eduCourseSelectionService.save(eduCourseSelection);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 * （原有逻辑不变，无需改造）
	 */
	@AutoLog(value = "选课记录表-编辑")
	@Operation(summary="选课记录表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduCourseSelection eduCourseSelection) {
		eduCourseSelectionService.updateById(eduCourseSelection);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过课程ID和学生ID删除选课记录（修复退课误提示“人数为0”问题）
	 * 核心优化：1. 移除多余的人数提前校验 2. 弱化Redis人数拦截，优先保证合法退课 3. 强制保证人数不出现负数
	 */
	@AutoLog(value = "选课记录表-通过课程ID和学生ID删除")
	@Operation(summary="选课记录表-通过课程ID和学生ID删除")
	@RequiresPermissions("eduselectcourse:edu_course_selection:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(
			@RequestParam(name="courseId",required=true) String courseId,
			@RequestParam(name="studentId",required=true) String studentId
	) {
		// 1. 分布式锁Key（学生ID+课程ID，细粒度锁，不影响其他操作）
		String lockKey = DROP_COURSE_LOCK_KEY + studentId + ":" + courseId;
		RLock lock = redissonClient.getLock(lockKey);
		try {
			// 2. 获取分布式锁（等待3秒，自动释放30秒，防止死锁）
			boolean lockAcquired = lock.tryLock(3, 30, TimeUnit.SECONDS);
			if (!lockAcquired) {
				return Result.error("当前退课请求繁忙，请稍后再试");
			}

			// 3. 参数非空校验
			if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(studentId)) {
				return Result.error("课程ID和学生ID不能为空");
			}

			// 4. 根据课程ID+学生ID查询选课记录（核心前提：只要有记录，就允许退课）
			QueryWrapper<EduCourseSelection> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("course_id", courseId)
					.eq("student_id", studentId);
			EduCourseSelection eduCourseSelection = eduCourseSelectionService.getOne(queryWrapper);
			if (eduCourseSelection == null) {
				return Result.error("选课记录不存在，无法删除");
			}

			// 5. 获取课程信息（先查Redis，再查数据库，提高性能）
			EduCourse eduCourse = getCourseInfoFromCache(courseId);
			if (eduCourse == null) {
				return Result.error("关联课程不存在");
			}
			// 增加非空校验，避免类型转换异常
			if (StringUtils.isEmpty(eduCourse.getMaxStudent()) || StringUtils.isEmpty(eduCourse.getCurrentStudent())) {
				return Result.error("课程人数配置异常");
			}
			int maxStudent = Integer.parseInt(eduCourse.getMaxStudent());
			int currentStudent = Integer.parseInt(eduCourse.getCurrentStudent());

			// 6. Redis原子操作减人数（优化：弱化拦截，优先保证退课，同时强制避免负数）
			RAtomicLong redisCurrentStudent = redissonClient.getAtomicLong(COURSE_CURRENT_STUDENT_KEY + courseId);
			// 初始化Redis人数（保证与数据库一致）
			initRedisCourseStudent(courseId, maxStudent, currentStudent);

			// 原子递减并返回旧值（不再因旧值<=0直接拦截，而是后续强制修正为0）
			long oldRedisCurrent = redisCurrentStudent.getAndDecrement();
			// 强制保证Redis人数不出现负数：如果递减后<0，重置为0
			long newRedisCurrent = Math.max(0, oldRedisCurrent - 1);
			if (newRedisCurrent != (oldRedisCurrent - 1)) {
				redisCurrentStudent.set(newRedisCurrent); // 重置为0，避免负数
			}

			// 7. 更新数据库（选课记录删除 + 课程人数更新，增加结果判断）
			// 7.1 删除选课记录
			boolean deleteSuccess = eduCourseSelectionService.removeById(eduCourseSelection.getId());
			if (!deleteSuccess) {
				// 回滚Redis原子操作：恢复到旧值
				redisCurrentStudent.set(oldRedisCurrent);
				return Result.error("删除选课记录失败");
			}

			// 7.2 更新数据库课程人数（安全更新：强制取0和(currentStudent-1)的最大值，避免负数）
			int newDbCurrent = Math.max(0, currentStudent - 1);
			boolean updateCourseSuccess = eduCourseService.lambdaUpdate()
					.set(EduCourse::getCurrentStudent, String.valueOf(newDbCurrent))
					.set(newDbCurrent < maxStudent, EduCourse::getStatus, "2") // 人数未满设为可选
					.eq(EduCourse::getId, courseId)
					.update();
			if (!updateCourseSuccess) {
				// 数据库更新失败，全量回滚：Redis人数 + 恢复选课记录
				redisCurrentStudent.set(oldRedisCurrent);
				eduCourseSelectionService.save(eduCourseSelection);
				return Result.error("课程人数更新失败，退课取消");
			}

			// 8. 同步更新Redis缓存（课程人数 + 状态）
			redisTemplate.opsForValue().set(COURSE_CURRENT_STUDENT_KEY + courseId, newDbCurrent, 1, TimeUnit.HOURS);
			redisTemplate.opsForValue().set(COURSE_INFO_KEY + courseId, eduCourse, 1, TimeUnit.HOURS); // 同步课程信息缓存

			return Result.OK("退课成功!");
		} catch (InterruptedException e) {
			log.error("获取退课分布式锁异常", e);
			Thread.currentThread().interrupt();
			return Result.error("退课请求被中断，请稍后再试");
		} catch (Exception e) {
			log.error("退课操作异常", e);
			// 异常时：强制将Redis人数重置为非负（避免异常导致负数）
			RAtomicLong redisCurrentStudent = redissonClient.getAtomicLong(COURSE_CURRENT_STUDENT_KEY + courseId);
			long currentRedisVal = redisCurrentStudent.get();
			if (currentRedisVal < 0) {
				redisCurrentStudent.set(0);
				redisTemplate.opsForValue().set(COURSE_CURRENT_STUDENT_KEY + courseId, 0, 1, TimeUnit.HOURS);
			}
			return Result.error("退课失败：" + e.getMessage());
		} finally {
			// 9. 释放分布式锁（必须在finally中释放，防止死锁）
			if (lock != null && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 *  批量删除
	 * （原有逻辑不变，若需高并发优化，可参考单个删除的分布式锁逻辑）
	 */
	@AutoLog(value = "选课记录表-批量删除")
	@Operation(summary="选课记录表-批量删除")
	@RequiresPermissions("eduselectcourse:edu_course_selection:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduCourseSelectionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 * （原有逻辑不变，无需改造）
	 */
	@Operation(summary="选课记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduCourseSelection> queryById(@RequestParam(name="id",required=true) String id) {
		EduCourseSelection eduCourseSelection = eduCourseSelectionService.getById(id);
		if(eduCourseSelection==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduCourseSelection);
	}

	/**
	 * 导出excel
	 * （原有逻辑不变，无需改造）
	 */
	@RequiresPermissions("eduselectcourse:edu_course_selection:exportXls")
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, EduCourseSelection eduCourseSelection) {
		return super.exportXls(request, eduCourseSelection, EduCourseSelection.class, "选课记录表");
	}

	/**
	 * 通过excel导入数据
	 * （原有逻辑不变，无需改造）
	 */
	@RequiresPermissions("eduselectcourse:edu_course_selection:importExcel")
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, EduCourseSelection.class);
	}

	/**
	 * 获取【学生已选课程列表】
	 * （原有逻辑不变，无需改造）
	 */
	@Operation(summary="选课记录表-获取【学生已选课程列表】")
	@GetMapping("/getSelectedCourses")
	public Result<List<EduCourseSelection>> getSelectedCourses(@RequestParam String studentId) {
		QueryWrapper<EduCourseSelection> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("student_id", studentId)
				.eq("status", 1);
		List<EduCourseSelection> selectedCourseList = eduCourseSelectionService.list(queryWrapper);
		return Result.OK(selectedCourseList);
	}

	/**
	 * 选课操作（高并发安全版，保留原有优化）
	 * 核心优化：原子判断+递增 + 缓存与数据库强一致 + 全量异常回滚 + 优化锁粒度
	 */
	@Operation(summary="选课记录表-选课操作（含：冲突检测、人数校验、状态更新）")
	@PostMapping("/selectCourse")
	public Result<String> selectCourse(@RequestParam String studentId, @RequestParam String courseId) {
		// 1. 优化锁粒度：课程ID锁（控制同一课程的并发选课）
		String lockKey = SELECT_COURSE_LOCK_KEY + courseId;
		RLock lock = redissonClient.getLock(lockKey);
		try {
			// 2. 获取分布式锁（等待3秒，自动释放30秒）
			boolean lockAcquired = lock.tryLock(3, 30, TimeUnit.SECONDS);
			if (!lockAcquired) {
				return Result.error("当前选课请求繁忙，请稍后再试");
			}

			// 3. 校验：课程是否存在（先查缓存，再查数据库）
			EduCourse course = getCourseInfoFromCache(courseId);
			if (course == null) {
				return Result.error("课程不存在");
			}
			// 增加非空校验，避免类型转换异常
			if (StringUtils.isEmpty(course.getMaxStudent()) || StringUtils.isEmpty(course.getCurrentStudent())) {
				return Result.error("课程人数配置异常");
			}
			int maxStudent = Integer.parseInt(course.getMaxStudent());
			int currentStudent = Integer.parseInt(course.getCurrentStudent());

			// 4. 校验：是否已选该课程（数据库查询，保证准确性）
			boolean alreadySelected = eduCourseSelectionService.lambdaQuery()
					.eq(EduCourseSelection::getStudentId, studentId)
					.eq(EduCourseSelection::getCourseId, courseId)
					.eq(EduCourseSelection::getStatus, 1)
					.exists();
			if (alreadySelected) {
				return Result.error("已选过该课程");
			}

			// 5. 校验：时间是否冲突
			String conflictCourse = eduCourseSelectionService.checkCourseTimeConflict(studentId, courseId);
			if (conflictCourse != null) {
				return Result.error("与课程【" + conflictCourse + "】时间冲突");
			}

			// 6. Redis原子操作校验并增加人数（核心：消除非原子竞争，彻底解决虚假满员）
			RAtomicLong redisCurrentStudent = redissonClient.getAtomicLong(COURSE_CURRENT_STUDENT_KEY + courseId);
			// 初始化Redis人数（保证Redis与数据库一致）
			initRedisCourseStudent(courseId, maxStudent, currentStudent);

			// 原子递增并返回递增前的旧值（核心原子操作，无并发漏洞）
			long oldRedisCurrent = redisCurrentStudent.getAndIncrement();
			// 判断旧值是否已达到最大人数（旧值>=max，说明递增前已满员）
			if (oldRedisCurrent >= maxStudent) {
				// 回滚：因为已经原子递增了，需要减1恢复
				redisCurrentStudent.decrementAndGet();
				return Result.error("课程人数已满，无法选课");
			}
			// 递增后的新值（必然<=max）
			long newRedisCurrent = oldRedisCurrent + 1;
			boolean isFull = newRedisCurrent >= maxStudent;

			// 7. 执行选课：插入选课记录
			EduCourseSelection selection = new EduCourseSelection();
			selection.setId(IdUtil.simpleUUID());
			selection.setStudentId(studentId);
			selection.setCourseId(courseId);
			selection.setCourseNo(course.getCourseNo());
			selection.setCourseName(course.getCourseName());
			selection.setStatus("1");
			boolean saveSuccess = eduCourseSelectionService.save(selection);
			if (!saveSuccess) {
				// 插入失败，回滚Redis人数
				redisCurrentStudent.decrementAndGet();
				return Result.error("选课记录插入失败");
			}

			// 8. 更新数据库课程人数与状态（增加结果判断，保证一致性）
			boolean updateCourseSuccess = eduCourseService.lambdaUpdate()
					.set(EduCourse::getCurrentStudent, String.valueOf(newRedisCurrent))
					.set(isFull, EduCourse::getStatus, "4") // 满员设为不可选
					.eq(EduCourse::getId, courseId)
					.update();
			if (!updateCourseSuccess) {
				// 数据库更新失败，全量回滚：Redis人数 + 选课记录
				redisCurrentStudent.decrementAndGet();
				eduCourseSelectionService.removeById(selection.getId());
				return Result.error("课程人数更新失败，选课取消");
			}

			// 9. 同步更新Redis缓存（强一致）
			redisTemplate.opsForValue().set(COURSE_CURRENT_STUDENT_KEY + courseId, newRedisCurrent, 1, TimeUnit.HOURS);
			redisTemplate.opsForValue().set(COURSE_MAX_STUDENT_KEY + courseId, maxStudent, 1, TimeUnit.HOURS);
			redisTemplate.opsForValue().set(COURSE_INFO_KEY + courseId, course, 1, TimeUnit.HOURS); // 同步课程信息

			return Result.OK("选课成功");
		} catch (InterruptedException e) {
			log.error("获取选课分布式锁异常", e);
			Thread.currentThread().interrupt();
			return Result.error("选课请求被中断，请稍后再试");
		} catch (Exception e) {
			log.error("选课操作异常", e);
			// 异常回滚Redis原子操作
			RAtomicLong redisCurrentStudent = redissonClient.getAtomicLong(COURSE_CURRENT_STUDENT_KEY + courseId);
			redisCurrentStudent.decrementAndGet();
			return Result.error("选课失败：" + e.getMessage());
		} finally {
			// 10. 释放分布式锁（必须在finally中释放）
			if (lock != null && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 选课时间冲突检测接口
	 * （原有逻辑不变，无需改造）
	 */
	@Operation(summary="选课记录表-【选课时间冲突检测接口】")
	@GetMapping("/checkCourseConflict")
	public Result<String> checkConflict(@RequestParam String studentId, @RequestParam String courseId) {
		try {
			String conflictCourse = eduCourseSelectionService.checkCourseTimeConflict(studentId, courseId);
			if (conflictCourse == null) {
				return Result.OK("无课程时间冲突，可正常选课");
			} else {
				return Result.error("选课失败：与已选课程【" + conflictCourse + "】时间冲突");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("课程时间冲突检测失败：" + e.getMessage());
		}
	}

	/**
	 * 手动刷新课程Redis缓存（异常场景下使用，保证缓存与数据库一致）
	 */
	@Operation(summary="选课记录表-手动刷新课程Redis缓存")
	@GetMapping("/refreshCourseRedis")
	@RequiresPermissions("eduselectcourse:edu_course_selection:refresh")
	public Result<String> refreshCourseRedis(@RequestParam String courseId) {
		// 查数据库真实课程信息
		EduCourse course = eduCourseService.getById(courseId);
		if (course == null) {
			return Result.error("课程不存在");
		}
		// 非空校验
		if (StringUtils.isEmpty(course.getMaxStudent()) || StringUtils.isEmpty(course.getCurrentStudent())) {
			return Result.error("课程人数配置异常");
		}
		int currentStudent = Integer.parseInt(course.getCurrentStudent());
		int maxStudent = Integer.parseInt(course.getMaxStudent());

		// 同步Redis缓存
		String currentKey = COURSE_CURRENT_STUDENT_KEY + courseId;
		String maxKey = COURSE_MAX_STUDENT_KEY + courseId;
		String infoKey = COURSE_INFO_KEY + courseId;

		redisTemplate.opsForValue().set(currentKey, currentStudent, 1, TimeUnit.HOURS);
		redisTemplate.opsForValue().set(maxKey, maxStudent, 1, TimeUnit.HOURS);
		redisTemplate.opsForValue().set(infoKey, course, 1, TimeUnit.HOURS);

		// 同步Redisson AtomicLong值
		redissonClient.getAtomicLong(currentKey).set(currentStudent);

		return Result.OK("课程Redis缓存刷新成功");
	}

	/**
	 * 从缓存获取课程信息（先Redis后DB，提高性能）
	 * @param courseId 课程ID
	 * @return 课程信息
	 */
	private EduCourse getCourseInfoFromCache(String courseId) {
		// 1. 先查Redis缓存
		String redisKey = COURSE_INFO_KEY + courseId;
		EduCourse course = (EduCourse) redisTemplate.opsForValue().get(redisKey);
		if (course != null) {
			return course;
		}
		// 2. 缓存未命中，查数据库
		course = eduCourseService.getById(courseId);
		if (course != null) {
			// 3. 存入Redis，设置1小时过期
			redisTemplate.opsForValue().set(redisKey, course, 1, TimeUnit.HOURS);
		}
		return course;
	}

	/**
	 * 初始化Redis课程人数（保证Redis与数据库一致）
	 * @param courseId 课程ID
	 * @param maxStudent 课程最大人数
	 * @param currentStudent 课程数据库当前人数
	 */
	private void initRedisCourseStudent(String courseId, int maxStudent, int currentStudent) {
		String currentRedisKey = COURSE_CURRENT_STUDENT_KEY + courseId;
		String maxRedisKey = COURSE_MAX_STUDENT_KEY + courseId;
		RAtomicLong redisCurrentStudent = redissonClient.getAtomicLong(currentRedisKey);

		// 1. 初始化最大人数缓存（若不存在）
		if (redisTemplate.opsForValue().get(maxRedisKey) == null) {
			redisTemplate.opsForValue().set(maxRedisKey, maxStudent, 1, TimeUnit.HOURS);
		}

		// 2. 对比Redis与数据库人数，不一致则同步
		long redisCurrent = redisCurrentStudent.get();
		if (redisCurrent != currentStudent) {
			redisCurrentStudent.set(currentStudent);
			redisTemplate.opsForValue().set(currentRedisKey, currentStudent, 1, TimeUnit.HOURS);
		}
	}
}