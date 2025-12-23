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
 * @Description: lost_claim
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("lost_claim")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="lost_claim")
public class LostClaim implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联lost_item.id（物品ID）*/
	@Excel(name = "关联lost_item.id（物品ID）", width = 15, dictTable = "lost_item", dicCode = "id", dicText = "item_name")
	@Dict(dictTable = "lost_item", dicCode = "id", dicText = "item_name")
    @Schema(description = "关联lost_item.id（物品ID）")
    private java.lang.Long itemId;
	/**关联sys_user.id（认领人ID）*/
	@Excel(name = "关联sys_user.id（认领人ID）", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Schema(description = "关联sys_user.id（认领人ID）")
    private java.lang.String claimUserId;
	/**认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）*/
	@Excel(name = "认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）", width = 15)
    @Schema(description = "认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）")
    private java.lang.String claimReason;
	/**审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）*/
	@Excel(name = "审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）", width = 15, dicCode = "AUDIT_STATUS")
	@Dict(dicCode = "AUDIT_STATUS")
    @Schema(description = "审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）")
    private java.lang.Integer auditStatus;
	/**关联sys_user.id（审核人ID，管理员）*/
	@Excel(name = "关联sys_user.id（审核人ID，管理员）", width = 15)
    @Schema(description = "关联sys_user.id（审核人ID，管理员）")
    private java.lang.String auditUserId;
	/**审核时间*/
	@Excel(name = "审核时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "审核时间")
    private java.util.Date auditTime;
	/**审核备注（如拒绝理由：“提供的特征与物品描述不符”）*/
	@Excel(name = "审核备注（如拒绝理由：“提供的特征与物品描述不符”）", width = 15)
    @Schema(description = "审核备注（如拒绝理由：“提供的特征与物品描述不符”）")
    private java.lang.String auditRemark;
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
