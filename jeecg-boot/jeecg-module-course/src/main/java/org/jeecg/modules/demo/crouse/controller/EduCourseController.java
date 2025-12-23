package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.crouse.entity.EduCourse;
import org.jeecg.modules.demo.crouse.service.IEduCourseService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: edu_course
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="edu_course")
@RestController
@RequestMapping("/crouse/eduCourse")
@Slf4j
public class EduCourseController extends JeecgController<EduCourse, IEduCourseService> {
	@Autowired
	private IEduCourseService eduCourseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduCourse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "edu_course-分页列表查询")
	@Operation(summary="edu_course-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduCourse>> queryPageList(EduCourse eduCourse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduCourse> queryWrapper = QueryGenerator.initQueryWrapper(eduCourse, req.getParameterMap());
		Page<EduCourse> page = new Page<EduCourse>(pageNo, pageSize);
		IPage<EduCourse> pageList = eduCourseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduCourse
	 * @return
	 */
	@AutoLog(value = "edu_course-添加")
	@Operation(summary="edu_course-添加")
	@RequiresPermissions("crouse:edu_course:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduCourse eduCourse) {
		eduCourseService.save(eduCourse);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduCourse
	 * @return
	 */
	@AutoLog(value = "edu_course-编辑")
	@Operation(summary="edu_course-编辑")
	@RequiresPermissions("crouse:edu_course:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduCourse eduCourse) {
		eduCourseService.updateById(eduCourse);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "edu_course-通过id删除")
	@Operation(summary="edu_course-通过id删除")
	@RequiresPermissions("crouse:edu_course:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eduCourseService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "edu_course-批量删除")
	@Operation(summary="edu_course-批量删除")
	@RequiresPermissions("crouse:edu_course:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduCourseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "edu_course-通过id查询")
	@Operation(summary="edu_course-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduCourse> queryById(@RequestParam(name="id",required=true) String id) {
		EduCourse eduCourse = eduCourseService.getById(id);
		if(eduCourse==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduCourse);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduCourse
    */
    @RequiresPermissions("crouse:edu_course:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduCourse eduCourse) {
        return super.exportXls(request, eduCourse, EduCourse.class, "edu_course");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("crouse:edu_course:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduCourse.class);
    }

}
