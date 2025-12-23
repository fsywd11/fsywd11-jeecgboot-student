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
 * @Description: lost_match
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("lost_match")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="lost_match")
public class LostMatch implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联lost_item.id（当前物品ID）*/
	@Excel(name = "关联lost_item.id（当前物品ID）", width = 15, dictTable = "lost_item", dicCode = "id", dicText = "item_name")
	@Dict(dictTable = "lost_item", dicCode = "id", dicText = "item_name")
    @Schema(description = "关联lost_item.id（当前物品ID）")
    private java.lang.Long itemId;
	/**关联lost_item.id（匹配的物品ID）*/
	@Excel(name = "关联lost_item.id（匹配的物品ID）", width = 15, dictTable = "lost_item", dicCode = "id", dicText = "item_name")
	@Dict(dictTable = "lost_item", dicCode = "id", dicText = "item_name")
    @Schema(description = "关联lost_item.id（匹配的物品ID）")
    private java.lang.Long matchItemId;
	/**匹配分数（0-100分，分数越高匹配度越高）*/
	@Excel(name = "匹配分数（0-100分，分数越高匹配度越高）", width = 15)
    @Schema(description = "匹配分数（0-100分，分数越高匹配度越高）")
    private java.lang.Integer matchScore;
	/**匹配时间*/
	@Excel(name = "匹配时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "匹配时间")
    private java.util.Date matchTime;
	/**是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）*/
	@Excel(name = "是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）", width = 15)
    @Schema(description = "是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）")
    private java.lang.Integer isNotify;
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
