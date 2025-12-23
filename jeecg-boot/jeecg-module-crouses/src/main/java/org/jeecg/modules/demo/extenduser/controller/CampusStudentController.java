package org.jeecg.modules.demo.extenduser.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.extenduser.entity.CampusStudent;
import org.jeecg.modules.demo.extenduser.service.ICampusStudentService;

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
 * @Description: campus_student
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="campus_student")
@RestController
@RequestMapping("/newstudent/campusStudent")
@Slf4j
public class CampusStudentController extends JeecgController<CampusStudent, ICampusStudentService> {
	@Autowired
	private ICampusStudentService campusStudentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param campusStudent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "campus_student-分页列表查询")
	@Operation(summary="campus_student-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CampusStudent>> queryPageList(CampusStudent campusStudent,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<CampusStudent> queryWrapper = QueryGenerator.initQueryWrapper(campusStudent, req.getParameterMap());
		Page<CampusStudent> page = new Page<CampusStudent>(pageNo, pageSize);
		IPage<CampusStudent> pageList = campusStudentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param campusStudent
	 * @return
	 */
	@AutoLog(value = "campus_student-添加")
	@Operation(summary="campus_student-添加")
	@RequiresPermissions("newstudent:campus_student:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CampusStudent campusStudent) {
		campusStudentService.save(campusStudent);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param campusStudent
	 * @return
	 */
	@AutoLog(value = "campus_student-编辑")
	@Operation(summary="campus_student-编辑")
	@RequiresPermissions("newstudent:campus_student:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CampusStudent campusStudent) {
		campusStudentService.updateById(campusStudent);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "campus_student-通过id删除")
	@Operation(summary="campus_student-通过id删除")
	@RequiresPermissions("newstudent:campus_student:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		campusStudentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "campus_student-批量删除")
	@Operation(summary="campus_student-批量删除")
	@RequiresPermissions("newstudent:campus_student:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.campusStudentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "campus_student-通过id查询")
	@Operation(summary="campus_student-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CampusStudent> queryById(@RequestParam(name="id",required=true) String id) {
		CampusStudent campusStudent = campusStudentService.getById(id);
		if(campusStudent==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(campusStudent);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param campusStudent
    */
    @RequiresPermissions("newstudent:campus_student:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CampusStudent campusStudent) {
        return super.exportXls(request, campusStudent, CampusStudent.class, "campus_student");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("newstudent:campus_student:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CampusStudent.class);
    }

}
