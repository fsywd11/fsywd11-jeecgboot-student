package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.EduStudent;
import org.jeecg.modules.demo.crouse.entity.EduTeacher;
import org.jeecg.modules.demo.crouse.service.IEduTeacherService;

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
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2025-12-25
 * @Version: V1.0
 */
@Tag(name="教师信息表")
@RestController
@RequestMapping("/test01/eduTeacher")
@Slf4j
public class EduTeacherController extends JeecgController<EduTeacher, IEduTeacherService> {
	@Autowired
	private IEduTeacherService eduTeacherService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduTeacher
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "教师信息表-分页列表查询")
	@Operation(summary="教师信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduTeacher>> queryPageList(EduTeacher eduTeacher,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduTeacher> queryWrapper = QueryGenerator.initQueryWrapper(eduTeacher, req.getParameterMap());
		Page<EduTeacher> page = new Page<EduTeacher>(pageNo, pageSize);
		IPage<EduTeacher> pageList = eduTeacherService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduTeacher
	 * @return
	 */
	@AutoLog(value = "教师信息表-添加")
	@Operation(summary="教师信息表-添加")
	@RequiresPermissions("eduteacher:edu_teacher:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduTeacher eduTeacher) {
		eduTeacherService.save(eduTeacher);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduTeacher
	 * @return
	 */
	@AutoLog(value = "教师信息表-编辑")
	@Operation(summary="教师信息表-编辑")
	@RequiresPermissions("eduteacher:edu_teacher:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduTeacher eduTeacher) {
		eduTeacherService.updateById(eduTeacher);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教师信息表-通过id删除")
	@Operation(summary="教师信息表-通过id删除")
	@RequiresPermissions("eduteacher:edu_teacher:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eduTeacherService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "教师信息表-批量删除")
	@Operation(summary="教师信息表-批量删除")
	@RequiresPermissions("eduteacher:edu_teacher:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduTeacherService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "教师信息表-通过id查询")
	@Operation(summary="教师信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduTeacher> queryById(@RequestParam(name="id",required=true) String id) {
		EduTeacher eduTeacher = eduTeacherService.getById(id);
		if(eduTeacher==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduTeacher);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduTeacher
    */
    @RequiresPermissions("eduteacher:edu_teacher:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduTeacher eduTeacher) {
        return super.exportXls(request, eduTeacher, EduTeacher.class, "教师信息表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("eduteacher:edu_teacher:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduTeacher.class);
    }


	 @Operation(summary="教师信息表-通过user_id查询")
	 @GetMapping(value = "/queryByUserId")
	 public Result<EduTeacher> queryByUserId(@RequestParam(name="userId",required=true) String userId) {
		 QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("user_id", userId);
		 EduTeacher eduTeacher = eduTeacherService.getOne(queryWrapper);
		 if(eduTeacher == null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(eduTeacher);
	 }

}
