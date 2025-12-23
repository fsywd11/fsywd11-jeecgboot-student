package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.LostItem;
import org.jeecg.modules.demo.crouse.service.ILostItemService;

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
 * @Description: lost_item
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="lost_item")
@RestController
@RequestMapping("/lostitem/lostItem")
@Slf4j
public class LostItemController extends JeecgController<LostItem, ILostItemService> {
	@Autowired
	private ILostItemService lostItemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lostItem
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "lost_item-分页列表查询")
	@Operation(summary="lost_item-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LostItem>> queryPageList(LostItem lostItem,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LostItem> queryWrapper = QueryGenerator.initQueryWrapper(lostItem, req.getParameterMap());
		Page<LostItem> page = new Page<LostItem>(pageNo, pageSize);
		IPage<LostItem> pageList = lostItemService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lostItem
	 * @return
	 */
	@AutoLog(value = "lost_item-添加")
	@Operation(summary="lost_item-添加")
	@RequiresPermissions("lostitem:lost_item:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LostItem lostItem) {
		lostItemService.save(lostItem);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lostItem
	 * @return
	 */
	@AutoLog(value = "lost_item-编辑")
	@Operation(summary="lost_item-编辑")
	@RequiresPermissions("lostitem:lost_item:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LostItem lostItem) {
		lostItemService.updateById(lostItem);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lost_item-通过id删除")
	@Operation(summary="lost_item-通过id删除")
	@RequiresPermissions("lostitem:lost_item:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lostItemService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "lost_item-批量删除")
	@Operation(summary="lost_item-批量删除")
	@RequiresPermissions("lostitem:lost_item:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lostItemService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "lost_item-通过id查询")
	@Operation(summary="lost_item-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LostItem> queryById(@RequestParam(name="id",required=true) String id) {
		LostItem lostItem = lostItemService.getById(id);
		if(lostItem==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lostItem);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lostItem
    */
    @RequiresPermissions("lostitem:lost_item:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LostItem lostItem) {
        return super.exportXls(request, lostItem, LostItem.class, "lost_item");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lostitem:lost_item:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LostItem.class);
    }

}
