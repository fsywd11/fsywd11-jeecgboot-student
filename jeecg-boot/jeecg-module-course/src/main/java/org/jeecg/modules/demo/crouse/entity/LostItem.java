package org.jeecg.modules.demo.crouse.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: lost_item
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("lost_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="lost_item")
public class LostItem implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联sys_user.id（发布人ID）*/
	@Excel(name = "关联sys_user.id（发布人ID）", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Schema(description = "关联sys_user.id（发布人ID）")
    private java.lang.String userId;
	/**物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）*/
	@Excel(name = "物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）", width = 15, dicCode = "ITEM_TYPES")
	@Dict(dicCode = "ITEM_TYPES")
    @Schema(description = "物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）")
    private java.lang.Integer itemType;
	/**物品性质：0失物/1招领*/
	@Excel(name = "物品性质：0失物/1招领", width = 15, dicCode = "ITEM_TYPE2")
	@Dict(dicCode = "ITEM_TYPE2")
    @Schema(description = "物品性质：0失物/1招领")
    private java.lang.Integer itemType2;
	/**物品名称（如黑色华为手机）*/
	@Excel(name = "物品名称（如黑色华为手机）", width = 15)
    @Schema(description = "物品名称（如黑色华为手机）")
    private java.lang.String itemName;
	/**物品描述（如机身有刻字“XXX”，带蓝色手机壳）*/
	@Excel(name = "物品描述（如机身有刻字“XXX”，带蓝色手机壳）", width = 15)
    @Schema(description = "物品描述（如机身有刻字“XXX”，带蓝色手机壳）")
    private java.lang.String itemDesc;
	/**丢失/捡到地点（如图书馆3楼阅览室）*/
	@Excel(name = "丢失/捡到地点（如图书馆3楼阅览室）", width = 15)
    @Schema(description = "丢失/捡到地点（如图书馆3楼阅览室）")
    private java.lang.String lostPlace;
	/**丢失/捡到时间*/
	@Excel(name = "丢失/捡到时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "丢失/捡到时间")
    private java.util.Date lostTime;
	/**物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）*/
	@Excel(name = "物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）", width = 15)
    @Schema(description = "物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）")
    private java.lang.String imageUrl;
	/**物品状态：0待认领/1待审核/2已认领/3已失效*/
	@Excel(name = "物品状态：0待认领/1待审核/2已认领/3已失效", width = 15, dicCode = "ITEM_STATUS")
	@Dict(dicCode = "ITEM_STATUS")
    @Schema(description = "物品状态：0待认领/1待审核/2已认领/3已失效")
    private java.lang.Integer status;
	/**联系电话（发布人电话，可选AES加密）*/
	@Excel(name = "联系电话（发布人电话，可选AES加密）", width = 15)
    @Schema(description = "联系电话（发布人电话，可选AES加密）")
    private java.lang.String contactPhone;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @Schema(description = "排序")
    private java.lang.Integer sort;
	/**创建人ID（关联sys_user.id）*/
    @Schema(description = "创建人ID（关联sys_user.id）")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private java.util.Date createTime;
	/**更新人ID（关联sys_user.id）*/
    @Schema(description = "更新人ID（关联sys_user.id）")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private java.util.Date updateTime;
	/**删除状态：0正常/1已删除*/
	@Excel(name = "删除状态：0正常/1已删除", width = 15)
    @Schema(description = "删除状态：0正常/1已删除")
    @TableLogic
    private java.lang.Integer delFlag;
}
