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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 选课记录表
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V1.0
 */
@Tag(name="选课记录表")
@RestController
@RequestMapping("/eduselectcourse/eduCourseSelection")
@Slf4j
public class EduCourseSelectionController extends JeecgController<EduCourseSelection, IEduCourseSelectionService> {
	@Autowired
	private IEduCourseSelectionService eduCourseSelectionService;

	@Autowired
	private IEduCourseService eduCourseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduCourseSelection
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "选课记录表-分页列表查询")
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
	 *
	 * @param eduCourseSelection
	 * @return
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
	 *
	 * @param eduCourseSelection
	 * @return
	 */
	@AutoLog(value = "选课记录表-编辑")
	@Operation(summary="选课记录表-编辑")
	@RequiresPermissions("eduselectcourse:edu_course_selection:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduCourseSelection eduCourseSelection) {
		eduCourseSelectionService.updateById(eduCourseSelection);
		return Result.OK("编辑成功!");
	}

	 /**
	  * 通过课程ID和学生ID删除选课记录
	  *
	  * @param courseId  课程ID
	  * @param studentId 学生ID
	  * @return 删除结果
	  */
	 @AutoLog(value = "选课记录表-通过课程ID和学生ID删除")
	 @Operation(summary="选课记录表-通过课程ID和学生ID删除")
	 @RequiresPermissions("eduselectcourse:edu_course_selection:delete")
	 @DeleteMapping(value = "/delete")
	 public Result<String> delete(
			 @RequestParam(name="courseId",required=true) String courseId,
			 @RequestParam(name="studentId",required=true) String studentId
	 ) {
		 // 1. 参数非空校验
		 if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(studentId)) {
			 return Result.error("课程ID和学生ID不能为空");
		 }

		 // 2. 根据课程ID+学生ID查询选课记录（核心修改点）
		 QueryWrapper<EduCourseSelection> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("course_id", courseId)
				 .eq("student_id", studentId);
		 EduCourseSelection eduCourseSelection = eduCourseSelectionService.getOne(queryWrapper);

		 // 3. 校验选课记录是否存在
		 if (eduCourseSelection == null) {
			 return Result.error("选课记录不存在，无法删除");
		 }

		 // 4. 查询课程信息并更新选课人数
		 EduCourse eduCourse = eduCourseService.getById(courseId);
		 if (eduCourse == null) {
			 return Result.error("关联课程不存在");
		 }

		 // 5. 安全更新课程当前人数（避免负数）
		 int currentStudent = 0;
		 if (eduCourse.getCurrentStudent() != null) {
			 try {
				 currentStudent = Integer.parseInt(eduCourse.getCurrentStudent());
			 } catch (NumberFormatException e) {
				 return Result.error("课程当前人数格式错误");
			 }
		 }
		 // 仅当人数>0时才减1
		 if (currentStudent > 0) {
			 eduCourse.setCurrentStudent(String.valueOf(currentStudent - 1));
			 eduCourseService.updateById(eduCourse);
		 }

		 // 6. 删除选课记录（使用实体ID删除，更安全）
		 boolean deleteSuccess = eduCourseSelectionService.removeById(eduCourseSelection.getId());
		 if (deleteSuccess) {
			 return Result.OK("退课成功!");
		 } else {
			 return Result.error("删除选课记录失败");
		 }
	 }

	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
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
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "选课记录表-通过id查询")
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
    *
    * @param request
    * @param eduCourseSelection
    */
    @RequiresPermissions("eduselectcourse:edu_course_selection:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduCourseSelection eduCourseSelection) {
        return super.exportXls(request, eduCourseSelection, EduCourseSelection.class, "选课记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("eduselectcourse:edu_course_selection:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduCourseSelection.class);
    }
	 /**
	  * 获取【学生已选课程列表】（仅返回选课记录实体列表，不关联课程详情）
	  */
	 @Operation(summary="选课记录表-获取【学生已选课程列表】")
	 @GetMapping("/getSelectedCourses")
	 public Result<List<EduCourseSelection>> getSelectedCourses(@RequestParam String studentId) {
		 // 使用QueryWrapper查询选课记录
		 QueryWrapper<EduCourseSelection> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("student_id", studentId)
				 .eq("status", 1); // 状态：1=正常选课

		 List<EduCourseSelection> selectedCourseList = eduCourseSelectionService.list(queryWrapper);
		 return Result.OK(selectedCourseList);
	 }

	 /**
	  * 选课操作（含：冲突检测、人数校验、状态更新）
	  */
	 @Operation(summary="选课记录表-选课操作（含：冲突检测、人数校验、状态更新）")
	 @PostMapping("/selectCourse")
	 public Result<String> selectCourse(@RequestParam String studentId, @RequestParam String courseId) {
		 // 1. 校验：课程是否存在且可选
		 EduCourse course = eduCourseService.getById(courseId);
		 if (course == null) {
			 return Result.error("课程不存在");
		 }



		 // 统一转换为数字，避免重复parse
		 int currentStudentNum = Integer.parseInt(course.getCurrentStudent());
		 int maxStudentNum = Integer.parseInt(course.getMaxStudent());

		 // 状态说明：建议统一枚举（比如 1=可选，2=不可选/满员）
		 // 修复：校验状态+人数满员
		 if (currentStudentNum >= maxStudentNum) {
			 course.setStatus("4");
			 eduCourseService.updateById(course);
			 return Result.error("课程不可选（已结束/人数已满）");
		 } else{
			 course.setStatus("2");
			 eduCourseService.updateById(course);
		 }


		 // 2. 校验：是否已选该课程
		 boolean alreadySelected = eduCourseSelectionService.lambdaQuery()
				 .eq(EduCourseSelection::getStudentId, studentId)
				 .eq(EduCourseSelection::getCourseId, courseId)
				 .eq(EduCourseSelection::getStatus, 1)
				 .exists();
		 if (alreadySelected) {
			 return Result.error("已选过该课程");
		 }

		 // 3. 校验：时间是否冲突（调用之前的冲突检测方法）
		 String conflictCourse = eduCourseSelectionService.checkCourseTimeConflict(studentId, courseId);
		 if (conflictCourse != null) {
			 return Result.error("与课程【" + conflictCourse + "】时间冲突");
		 }


		 //根据用户id获取学生信息


		 // 4. 执行选课：插入选课记录 + 更新课程当前人数
		 // 4.1 插入选课记录
		 EduCourseSelection selection = new EduCourseSelection();
		 selection.setId(IdUtil.simpleUUID());
		 selection.setStudentId(studentId);
		 selection.setCourseId(courseId);
		 selection.setCourseNo(course.getCourseNo());
		 selection.setCourseName(course.getCourseName());
		 selection.setStatus("1");
		 eduCourseSelectionService.save(selection);

		 // 4.2 更新课程当前人数（+1），若满员则改状态为“不可选”
		 int newCurrentStudent = currentStudentNum + 1; // 数字加法，正确更新
		 boolean isFull = newCurrentStudent >= maxStudentNum;

		 // 修复1：转换为字符串存入数据库（匹配字段类型）
		 // 修复2：满员后状态设为2（不可选），未滿员保持1（可选）
		 eduCourseService.lambdaUpdate()
				 .set(EduCourse::getCurrentStudent, String.valueOf(newCurrentStudent))
				 .set(isFull, EduCourse::getStatus, "2") // 满员→不可选（status=2）
				 .eq(EduCourse::getId, courseId)
				 .update();

		 return Result.OK("选课成功");
	 }

	 /**
	  * 选课时间冲突检测接口（JeecgBoot标准优化版）
	  * @param studentId 学生ID
	  * @param courseId 待选课程ID
	  * @return JeecgBoot标准Result对象
	  */
	 @Operation(summary="选课记录表-【选课时间冲突检测接口】")
	 @GetMapping("/checkCourseConflict")
	 public Result<String> checkConflict(@RequestParam String studentId, @RequestParam String courseId) {
		 try {
			 // 1. 调用业务层冲突检测方法
			 String conflictCourse = eduCourseSelectionService.checkCourseTimeConflict(studentId, courseId);

			 // 2. 无冲突：返回成功状态 + 提示信息
			 if (conflictCourse == null) {
				 return Result.OK("无课程时间冲突，可正常选课");
			 }
			 // 3. 有冲突：返回失败状态 + 冲突提示（注意：用Result.error，而非直接返回字符串）
			 else {
				 return Result.error("选课失败：与已选课程【" + conflictCourse + "】时间冲突");
			 }
		 } catch (Exception e) {
			 // 4. 异常捕获：返回失败状态 + 异常提示，便于问题排查
			 e.printStackTrace(); // 后端日志打印异常详情
			 return Result.error("课程时间冲突检测失败：" + e.getMessage());
		 }
	 }


}
