package org.jeecg.modules.demo.newLostAndFound.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 失物招领表
 * @Author: jeecg-boot
 * @Date:   2025-12-31
 * @Version: V1.0
 */
@Data
@TableName("lost_and_found")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="失物招领表")
public class LostAndFound implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private String sysOrgCode;
	/**发布人ID*/
	@Excel(name = "发布人ID", width = 15)
    @Schema(description = "发布人ID")
    private String userId;
	/**发布人姓名*/
	@Excel(name = "发布人姓名", width = 15)
    @Schema(description = "发布人姓名")
    private String userName;
	/**类型*/
	@Excel(name = "类型", width = 15, dicCode = "losttype")
	@Dict(dicCode = "losttype")
    @Schema(description = "类型")
    private String type;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @Schema(description = "标题")
    private String title;
	/**详细描述*/
	@Excel(name = "详细描述", width = 15)
    @Schema(description = "详细描述")
    private String content;
	/**物品分类*/
	@Excel(name = "物品分类", width = 15, dicCode = "lostitem_category")
	@Dict(dicCode = "lostitem_category")
    @Schema(description = "物品分类")
    private String itemCategory;
	/**丢失/拾取地点*/
	@Excel(name = "丢失/拾取地点", width = 15)
    @Schema(description = "丢失/拾取地点")
    private String location;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "loststatus")
	@Dict(dicCode = "loststatus")
    @Schema(description = "状态")
    private String status;
	/**认领人ID*/
	@Excel(name = "认领人ID", width = 15)
    @Schema(description = "认领人ID")
    private String claimUserId;
	/**认领人姓名*/
	@Excel(name = "认领人姓名", width = 15)
    @Schema(description = "认领人姓名")
    private String claimUserName;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @Schema(description = "图片")
    private String image;
}
