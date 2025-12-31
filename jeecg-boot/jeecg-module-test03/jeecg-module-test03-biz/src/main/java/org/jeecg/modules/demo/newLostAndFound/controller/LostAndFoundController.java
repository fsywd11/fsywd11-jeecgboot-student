package org.jeecg.modules.demo.newLostAndFound.controller;

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
import org.jeecg.modules.demo.newLostAndFound.entity.LostAndFound;
import org.jeecg.modules.demo.newLostAndFound.service.ILostAndFoundService;

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
 * @Description: 失物招领表
 * @Author: jeecg-boot
 * @Date:   2025-12-31
 * @Version: V1.0
 */
@Tag(name="失物招领表")
@RestController
@RequestMapping("/test03/lostAndFound")
@Slf4j
public class LostAndFoundController extends JeecgController<LostAndFound, ILostAndFoundService> {
	@Autowired
	private ILostAndFoundService lostAndFoundService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lostAndFound
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "失物招领表-分页列表查询")
	@Operation(summary="失物招领表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LostAndFound>> queryPageList(LostAndFound lostAndFound,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="claim-user-id", required=false) String claimUserId,
								   HttpServletRequest req) {


        QueryWrapper<LostAndFound> queryWrapper = QueryGenerator.initQueryWrapper(lostAndFound, req.getParameterMap());
		Page<LostAndFound> page = new Page<LostAndFound>(pageNo, pageSize);
		IPage<LostAndFound> pageList = lostAndFoundService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lostAndFound
	 * @return
	 */
	@AutoLog(value = "失物招领表-添加")
	@Operation(summary="失物招领表-添加")
	@RequiresPermissions("newLostAndFound:lost_and_found:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LostAndFound lostAndFound) {
		lostAndFoundService.save(lostAndFound);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lostAndFound
	 * @return
	 */
	@AutoLog(value = "失物招领表-编辑")
	@Operation(summary="失物招领表-编辑")
	@RequiresPermissions("newLostAndFound:lost_and_found:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LostAndFound lostAndFound) {
		lostAndFoundService.updateById(lostAndFound);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "失物招领表-通过id删除")
	@Operation(summary="失物招领表-通过id删除")
	@RequiresPermissions("newLostAndFound:lost_and_found:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lostAndFoundService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "失物招领表-批量删除")
	@Operation(summary="失物招领表-批量删除")
	@RequiresPermissions("newLostAndFound:lost_and_found:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lostAndFoundService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "失物招领表-通过id查询")
	@Operation(summary="失物招领表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LostAndFound> queryById(@RequestParam(name="id",required=true) String id) {
		LostAndFound lostAndFound = lostAndFoundService.getById(id);
		if(lostAndFound==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lostAndFound);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lostAndFound
    */
    @RequiresPermissions("newLostAndFound:lost_and_found:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LostAndFound lostAndFound) {
        return super.exportXls(request, lostAndFound, LostAndFound.class, "失物招领表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("newLostAndFound:lost_and_found:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LostAndFound.class);
    }

}
