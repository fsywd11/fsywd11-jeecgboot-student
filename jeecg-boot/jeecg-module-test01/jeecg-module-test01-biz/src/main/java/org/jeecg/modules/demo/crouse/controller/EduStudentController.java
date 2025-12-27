package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.EduStudent;
import org.jeecg.modules.demo.crouse.service.IEduStudentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2025-12-25
 * @Version: V1.0
 */
@Tag(name="学生信息表")
@RestController
@RequestMapping("/test01/eduStudent")
@Slf4j
public class EduStudentController extends JeecgController<EduStudent, IEduStudentService> {
	@Autowired
	private IEduStudentService eduStudentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduStudent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "学生信息表-分页列表查询")
	@Operation(summary="学生信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduStudent>> queryPageList(EduStudent eduStudent,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduStudent> queryWrapper = QueryGenerator.initQueryWrapper(eduStudent, req.getParameterMap());
		Page<EduStudent> page = new Page<EduStudent>(pageNo, pageSize);
		IPage<EduStudent> pageList = eduStudentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduStudent
	 * @return
	 */
	@AutoLog(value = "学生信息表-添加")
	@Operation(summary="学生信息表-添加")
	@RequiresPermissions("edustudent:edu_student:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduStudent eduStudent) {
		eduStudentService.save(eduStudent);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduStudent
	 * @return
	 */
	@AutoLog(value = "学生信息表-编辑")
	@Operation(summary="学生信息表-编辑")
	@RequiresPermissions("edustudent:edu_student:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduStudent eduStudent) {
		eduStudentService.updateById(eduStudent);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生信息表-通过id删除")
	@Operation(summary="学生信息表-通过id删除")
	@RequiresPermissions("edustudent:edu_student:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eduStudentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生信息表-批量删除")
	@Operation(summary="学生信息表-批量删除")
	@RequiresPermissions("edustudent:edu_student:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduStudentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "学生信息表-通过id查询")
	@Operation(summary="学生信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduStudent> queryById(@RequestParam(name="id",required=true) String id) {
		EduStudent eduStudent = eduStudentService.getById(id);
		if(eduStudent==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduStudent);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduStudent
    */
    @RequiresPermissions("edustudent:edu_student:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduStudent eduStudent) {
        return super.exportXls(request, eduStudent, EduStudent.class, "学生信息表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("edustudent:edu_student:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduStudent.class);
    }


	 @Operation(summary="学生信息表-通过user_id查询")
	 @GetMapping(value = "/queryByUserId")
	 public Result<EduStudent> queryByUserId(@RequestParam(name="userId",required=true) String userId) {
		 QueryWrapper<EduStudent> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("user_id", userId);
		 EduStudent eduStudent = eduStudentService.getOne(queryWrapper);
		 if(eduStudent == null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(eduStudent);
	 }

}
