package org.jeecg.modules.demo.extenduser.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.extenduser.entity.CampusTeacher;
import org.jeecg.modules.demo.extenduser.service.ICampusTeacherService;

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
 * @Description: campus_teacher
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="campus_teacher")
@RestController
@RequestMapping("/teachers/campusTeacher")
@Slf4j
public class CampusTeacherController extends JeecgController<CampusTeacher, ICampusTeacherService> {
	@Autowired
	private ICampusTeacherService campusTeacherService;
	
	/**
	 * 分页列表查询
	 *
	 * @param campusTeacher
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "campus_teacher-分页列表查询")
	@Operation(summary="campus_teacher-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CampusTeacher>> queryPageList(CampusTeacher campusTeacher,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<CampusTeacher> queryWrapper = QueryGenerator.initQueryWrapper(campusTeacher, req.getParameterMap());
		Page<CampusTeacher> page = new Page<CampusTeacher>(pageNo, pageSize);
		IPage<CampusTeacher> pageList = campusTeacherService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param campusTeacher
	 * @return
	 */
	@AutoLog(value = "campus_teacher-添加")
	@Operation(summary="campus_teacher-添加")
	@RequiresPermissions("teachers:campus_teacher:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CampusTeacher campusTeacher) {
		campusTeacherService.save(campusTeacher);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param campusTeacher
	 * @return
	 */
	@AutoLog(value = "campus_teacher-编辑")
	@Operation(summary="campus_teacher-编辑")
	@RequiresPermissions("teachers:campus_teacher:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CampusTeacher campusTeacher) {
		campusTeacherService.updateById(campusTeacher);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "campus_teacher-通过id删除")
	@Operation(summary="campus_teacher-通过id删除")
	@RequiresPermissions("teachers:campus_teacher:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		campusTeacherService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "campus_teacher-批量删除")
	@Operation(summary="campus_teacher-批量删除")
	@RequiresPermissions("teachers:campus_teacher:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.campusTeacherService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "campus_teacher-通过id查询")
	@Operation(summary="campus_teacher-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CampusTeacher> queryById(@RequestParam(name="id",required=true) String id) {
		CampusTeacher campusTeacher = campusTeacherService.getById(id);
		if(campusTeacher==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(campusTeacher);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param campusTeacher
    */
    @RequiresPermissions("teachers:campus_teacher:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CampusTeacher campusTeacher) {
        return super.exportXls(request, campusTeacher, CampusTeacher.class, "campus_teacher");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("teachers:campus_teacher:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CampusTeacher.class);
    }

}
