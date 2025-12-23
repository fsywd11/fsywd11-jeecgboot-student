package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.EduSelection;
import org.jeecg.modules.demo.crouse.service.IEduSelectionService;

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
 * @Description: edu_selection
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="edu_selection")
@RestController
@RequestMapping("/eduselection/eduSelection")
@Slf4j
public class EduSelectionController extends JeecgController<EduSelection, IEduSelectionService> {
	@Autowired
	private IEduSelectionService eduSelectionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduSelection
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "edu_selection-分页列表查询")
	@Operation(summary="edu_selection-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduSelection>> queryPageList(EduSelection eduSelection,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduSelection> queryWrapper = QueryGenerator.initQueryWrapper(eduSelection, req.getParameterMap());
		Page<EduSelection> page = new Page<EduSelection>(pageNo, pageSize);
		IPage<EduSelection> pageList = eduSelectionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduSelection
	 * @return
	 */
	@AutoLog(value = "edu_selection-添加")
	@Operation(summary="edu_selection-添加")
	@RequiresPermissions("eduselection:edu_selection:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduSelection eduSelection) {
		eduSelectionService.save(eduSelection);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduSelection
	 * @return
	 */
	@AutoLog(value = "edu_selection-编辑")
	@Operation(summary="edu_selection-编辑")
	@RequiresPermissions("eduselection:edu_selection:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduSelection eduSelection) {
		eduSelectionService.updateById(eduSelection);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "edu_selection-通过id删除")
	@Operation(summary="edu_selection-通过id删除")
	@RequiresPermissions("eduselection:edu_selection:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eduSelectionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "edu_selection-批量删除")
	@Operation(summary="edu_selection-批量删除")
	@RequiresPermissions("eduselection:edu_selection:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduSelectionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "edu_selection-通过id查询")
	@Operation(summary="edu_selection-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduSelection> queryById(@RequestParam(name="id",required=true) String id) {
		EduSelection eduSelection = eduSelectionService.getById(id);
		if(eduSelection==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduSelection);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduSelection
    */
    @RequiresPermissions("eduselection:edu_selection:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduSelection eduSelection) {
        return super.exportXls(request, eduSelection, EduSelection.class, "edu_selection");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("eduselection:edu_selection:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduSelection.class);
    }

}
