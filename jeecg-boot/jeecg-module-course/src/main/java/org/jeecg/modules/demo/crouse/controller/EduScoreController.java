package org.jeecg.modules.demo.crouse.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.crouse.entity.EduScore;
import org.jeecg.modules.demo.crouse.service.IEduScoreService;

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
 * @Description: edu_score
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Tag(name="edu_score")
@RestController
@RequestMapping("/eduscore/eduScore")
@Slf4j
public class EduScoreController extends JeecgController<EduScore, IEduScoreService> {
	@Autowired
	private IEduScoreService eduScoreService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduScore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "edu_score-分页列表查询")
	@Operation(summary="edu_score-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EduScore>> queryPageList(EduScore eduScore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<EduScore> queryWrapper = QueryGenerator.initQueryWrapper(eduScore, req.getParameterMap());
		Page<EduScore> page = new Page<EduScore>(pageNo, pageSize);
		IPage<EduScore> pageList = eduScoreService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduScore
	 * @return
	 */
	@AutoLog(value = "edu_score-添加")
	@Operation(summary="edu_score-添加")
	@RequiresPermissions("eduscore:edu_score:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EduScore eduScore) {
		eduScoreService.save(eduScore);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduScore
	 * @return
	 */
	@AutoLog(value = "edu_score-编辑")
	@Operation(summary="edu_score-编辑")
	@RequiresPermissions("eduscore:edu_score:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EduScore eduScore) {
		eduScoreService.updateById(eduScore);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "edu_score-通过id删除")
	@Operation(summary="edu_score-通过id删除")
	@RequiresPermissions("eduscore:edu_score:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		eduScoreService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "edu_score-批量删除")
	@Operation(summary="edu_score-批量删除")
	@RequiresPermissions("eduscore:edu_score:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eduScoreService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "edu_score-通过id查询")
	@Operation(summary="edu_score-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EduScore> queryById(@RequestParam(name="id",required=true) String id) {
		EduScore eduScore = eduScoreService.getById(id);
		if(eduScore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(eduScore);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduScore
    */
    @RequiresPermissions("eduscore:edu_score:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduScore eduScore) {
        return super.exportXls(request, eduScore, EduScore.class, "edu_score");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("eduscore:edu_score:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EduScore.class);
    }

}
