package org.jeecg.modules.demo.extenduser.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.extenduser.entity.EduCourseSelection;
import org.jeecg.modules.demo.extenduser.entity.EduStudentScore;
import org.jeecg.modules.demo.extenduser.service.IEduCourseSelectionService;
import org.jeecg.modules.demo.extenduser.service.IEduStudentScoreService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2025-12-28
 * @Version: V1.0
 */
@Tag(name="学生成绩表")
@RestController
@RequestMapping("/test02/eduStudentScore")
@Slf4j
@EnableScheduling // 若同时使用其他定时任务可保留
@EnableAsync // 开启异步支持
public class EduStudentScoreController extends JeecgController<EduStudentScore, IEduStudentScoreService> {
	@Autowired
	private IEduStudentScoreService eduStudentScoreService;

	@Autowired
	protected IEduCourseSelectionService eduCourseSelectionService;



	 private ScheduledExecutorService scheduler;

	 // 7天对应的毫秒数：7 * 24小时 * 60分 * 60秒 * 1000毫秒7L * 24 * 60 * 60 * 1000
	 private static final long SEVEN_DAYS_DELAY = 30000;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduStudentScore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "学生成绩表-分页列表查询")
	@Operation(summary="学生成绩表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduStudentScore>> queryPageList(EduStudentScore eduStudentScore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduStudentScore> queryWrapper = QueryGenerator.initQueryWrapper(eduStudentScore, req.getParameterMap());
		Page<EduStudentScore> page = new Page<EduStudentScore>(pageNo, pageSize);
		IPage<EduStudentScore> pageList = eduStudentScoreService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduStudentScore
	 * @return
	 */
	@AutoLog(value = "学生成绩表-添加")
	@Operation(summary="学生成绩表-添加")
	@RequiresPermissions("EduStudentScore:edu_student_score:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduStudentScore eduStudentScore) {
		eduStudentScoreService.save(eduStudentScore);
		if(Objects.equals(eduStudentScore.getScoreStatus(), "3")){
			eduStudentScoreService.sevenDaysLaterBusiness();
		}
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduStudentScore
	 * @return
	 */
	@AutoLog(value = "学生成绩表-编辑")
	@Operation(summary="学生成绩表-编辑")
	@RequiresPermissions("EduStudentScore:edu_student_score:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduStudentScore eduStudentScore) {
		eduStudentScoreService.updateById(eduStudentScore);
		if(Objects.equals(eduStudentScore.getScoreStatus(), "3")){
			eduStudentScoreService.sevenDaysLaterBusiness();
		}
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩表-通过id删除")
	@Operation(summary="学生成绩表-通过id删除")
	@RequiresPermissions("EduStudentScore:edu_student_score:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		// 首先检查成绩记录是否存在
		EduStudentScore eduStudentScore = eduStudentScoreService.getById(id);
		if (eduStudentScore == null) {
			return Result.error("成绩记录不存在，无法删除");
		}

		// 获取学生ID用于后续删除选课记录
		String studentId = eduStudentScore.getStudentId();

		// 删除成绩记录
		boolean removeResult = eduStudentScoreService.removeById(id);
		if (!removeResult) {
			return Result.error("删除成绩记录失败");
		}

		// 删除关联的选课记录
		eduCourseSelectionService.remove(new QueryWrapper<EduCourseSelection>().eq("student_id", studentId));

		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生成绩表-批量删除")
	@Operation(summary="学生成绩表-批量删除")
	@RequiresPermissions("EduStudentScore:edu_student_score:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduStudentScoreService.removeByIds(Arrays.asList(ids.split(",")));
		//批量删除课程选课记录，选出学生id
		//首先是得到全部的学生id
		List<String> studentIdList = eduStudentScoreService.list(new QueryWrapper<EduStudentScore>().select("student_id")).stream().map(EduStudentScore::getStudentId).collect(Collectors.toList());
		eduCourseSelectionService.remove(new QueryWrapper<EduCourseSelection>().in("student_id", studentIdList));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "学生成绩表-通过id查询")
	@Operation(summary="学生成绩表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduStudentScore> queryById(@RequestParam(name="id",required=true) String id) {
		EduStudentScore eduStudentScore = eduStudentScoreService.getById(id);
		if(eduStudentScore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduStudentScore);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduStudentScore
    */
    @RequiresPermissions("EduStudentScore:edu_student_score:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduStudentScore eduStudentScore) {
        return super.exportXls(request, eduStudentScore, EduStudentScore.class, "学生成绩表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("EduStudentScore:edu_student_score:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduStudentScore.class);
    }


	 /**
	  * 根据学生id分页列表查询
	  *
	  * @param eduStudentScore
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "学生成绩表-分页列表查询")
	 @Operation(summary="学生成绩表-学生成绩分页列表查询")
	 @GetMapping(value = "/studentlist")
	 public Result<IPage<EduStudentScore>> queryPageListByStudentId(EduStudentScore eduStudentScore,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 @RequestParam(name="studentId", required=true) String studentId,
														 HttpServletRequest req) {


		 QueryWrapper<EduStudentScore> queryWrapper = QueryGenerator.initQueryWrapper(eduStudentScore, req.getParameterMap());
		 // 添加学生ID的查询条件
		 queryWrapper.eq("student_id", studentId);

		 Page<EduStudentScore> page = new Page<EduStudentScore>(pageNo, pageSize);
		 IPage<EduStudentScore> pageList = eduStudentScoreService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }


	 /**
	  * 根据教师id分页列表查询
	  *
	  * @param eduStudentScore
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "学生成绩表-教师选课学生分页列表查询")
	 @Operation(summary="学生成绩表-教师选课学生分页列表查询")
	 @GetMapping(value = "/teacherlist")
	 public Result<IPage<EduStudentScore>> queryPageListByTeacherId(EduStudentScore eduStudentScore,
																	@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																	@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																	@RequestParam(name="teacherId", required=true) String teacherId,
																	HttpServletRequest req) {


		 QueryWrapper<EduStudentScore> queryWrapper = QueryGenerator.initQueryWrapper(eduStudentScore, req.getParameterMap());
		 // 添加教师ID的查询条件
		 queryWrapper.eq("teacher_id", teacherId);
		 Page<EduStudentScore> page = new Page<EduStudentScore>(pageNo, pageSize);
		 IPage<EduStudentScore> pageList = eduStudentScoreService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }


//
//
//	 //自动检查成绩，若是课程已结课，则自动计算成绩的绩点和综合成绩，根据成绩和占比，并在7天后执行
//	 /**
//	  * 项目启动后，自动触发7天后的一次性任务
//	  * @PostConstruct：组件初始化完成后执行（用于触发延迟任务）
//	  */
//	 @PostConstruct
//	 public void initSevenDaysLaterTask() {
//		 // 创建单线程调度器（用于执行延迟任务）
//		 scheduler = Executors.newSingleThreadScheduledExecutor();
//		 // 调度任务：延迟 SEVEN_DAYS_DELAY 后，执行指定任务
//		 scheduler.schedule(this::sevenDaysLaterBusiness, SEVEN_DAYS_DELAY, TimeUnit.MILLISECONDS);
//
//	 }
//
//	 /**
//	  * 7天后要执行的业务逻辑（核心业务代码）
//	  */
//	 @Async // 异步执行，不阻塞主线程（可选，推荐添加）
//	 public void sevenDaysLaterBusiness() {
//		 try {
//			 // 执行结算业务
//			 eduStudentScoreService.sevenDaysLaterBusiness();
//			 log.info("结算任务执行完成，时间：{}", new java.util.Date());
//		 } finally {
//			 // 任务执行完成后，关闭调度器（避免线程泄露）
//			 if (scheduler != null && !scheduler.isShutdown()) {
//				 scheduler.shutdown();
//			 }
//		 }
//	 }
}
