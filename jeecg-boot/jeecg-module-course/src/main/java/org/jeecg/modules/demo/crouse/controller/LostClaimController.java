package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.LostClaim;
import org.jeecg.modules.demo.crouse.service.ILostClaimService;

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
 * @Description: lost_claim
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="lost_claim")
@RestController
@RequestMapping("/claims/lostClaim")
@Slf4j
public class LostClaimController extends JeecgController<LostClaim, ILostClaimService> {
	@Autowired
	private ILostClaimService lostClaimService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lostClaim
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "lost_claim-分页列表查询")
	@Operation(summary="lost_claim-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LostClaim>> queryPageList(LostClaim lostClaim,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LostClaim> queryWrapper = QueryGenerator.initQueryWrapper(lostClaim, req.getParameterMap());
		Page<LostClaim> page = new Page<LostClaim>(pageNo, pageSize);
		IPage<LostClaim> pageList = lostClaimService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lostClaim
	 * @return
	 */
	@AutoLog(value = "lost_claim-添加")
	@Operation(summary="lost_claim-添加")
	@RequiresPermissions("claims:lost_claim:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LostClaim lostClaim) {
		lostClaimService.save(lostClaim);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lostClaim
	 * @return
	 */
	@AutoLog(value = "lost_claim-编辑")
	@Operation(summary="lost_claim-编辑")
	@RequiresPermissions("claims:lost_claim:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LostClaim lostClaim) {
		lostClaimService.updateById(lostClaim);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lost_claim-通过id删除")
	@Operation(summary="lost_claim-通过id删除")
	@RequiresPermissions("claims:lost_claim:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lostClaimService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "lost_claim-批量删除")
	@Operation(summary="lost_claim-批量删除")
	@RequiresPermissions("claims:lost_claim:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lostClaimService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "lost_claim-通过id查询")
	@Operation(summary="lost_claim-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LostClaim> queryById(@RequestParam(name="id",required=true) String id) {
		LostClaim lostClaim = lostClaimService.getById(id);
		if(lostClaim==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lostClaim);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lostClaim
    */
    @RequiresPermissions("claims:lost_claim:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LostClaim lostClaim) {
        return super.exportXls(request, lostClaim, LostClaim.class, "lost_claim");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("claims:lost_claim:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LostClaim.class);
    }

}
