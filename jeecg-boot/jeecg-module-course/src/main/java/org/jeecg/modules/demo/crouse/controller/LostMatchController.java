package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import org.jeecg.modules.demo.crouse.entity.LostMatch;
import org.jeecg.modules.demo.crouse.service.ILostMatchService;

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
 * @Description: lost_match
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="lost_match")
@RestController
@RequestMapping("/matchs/lostMatch")
@Slf4j
public class LostMatchController extends JeecgController<LostMatch, ILostMatchService> {
	@Autowired
	private ILostMatchService lostMatchService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lostMatch
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "lost_match-分页列表查询")
	@Operation(summary="lost_match-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LostMatch>> queryPageList(LostMatch lostMatch,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LostMatch> queryWrapper = QueryGenerator.initQueryWrapper(lostMatch, req.getParameterMap());
		Page<LostMatch> page = new Page<LostMatch>(pageNo, pageSize);
		IPage<LostMatch> pageList = lostMatchService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lostMatch
	 * @return
	 */
	@AutoLog(value = "lost_match-添加")
	@Operation(summary="lost_match-添加")
	@RequiresPermissions("matchs:lost_match:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LostMatch lostMatch) {
		lostMatchService.save(lostMatch);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lostMatch
	 * @return
	 */
	@AutoLog(value = "lost_match-编辑")
	@Operation(summary="lost_match-编辑")
	@RequiresPermissions("matchs:lost_match:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LostMatch lostMatch) {
		lostMatchService.updateById(lostMatch);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lost_match-通过id删除")
	@Operation(summary="lost_match-通过id删除")
	@RequiresPermissions("matchs:lost_match:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lostMatchService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "lost_match-批量删除")
	@Operation(summary="lost_match-批量删除")
	@RequiresPermissions("matchs:lost_match:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lostMatchService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "lost_match-通过id查询")
	@Operation(summary="lost_match-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LostMatch> queryById(@RequestParam(name="id",required=true) String id) {
		LostMatch lostMatch = lostMatchService.getById(id);
		if(lostMatch==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lostMatch);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lostMatch
    */
    @RequiresPermissions("matchs:lost_match:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LostMatch lostMatch) {
        return super.exportXls(request, lostMatch, LostMatch.class, "lost_match");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("matchs:lost_match:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LostMatch.class);
    }

}
