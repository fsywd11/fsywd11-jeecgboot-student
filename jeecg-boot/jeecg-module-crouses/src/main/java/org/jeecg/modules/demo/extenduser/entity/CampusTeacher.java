package org.jeecg.modules.demo.extenduser.entity;

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
 * @Description: campus_teacher
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("campus_teacher")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="campus_teacher")
public class CampusTeacher implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联campus_user_extend.id（用户扩展表主键）*/
	@Excel(name = "关联campus_user_extend.id（用户扩展表主键）", width = 15, dictTable = "campus_user_extend", dicCode = "id", dicText = "user_id")
	@Dict(dictTable = "campus_user_extend", dicCode = "id", dicText = "user_id")
    @Schema(description = "关联campus_user_extend.id（用户扩展表主键）")
    private java.lang.Long extendId;
	/**工号（唯一，参考sys_user.work_no）*/
	@Excel(name = "工号（唯一，参考sys_user.work_no）", width = 15)
    @Schema(description = "工号（唯一，参考sys_user.work_no）")
    private java.lang.String teacherNo;
	/**部门（如计算机学院教学办）*/
	@Excel(name = "部门（如计算机学院教学办）", width = 15, dicCode = "dept")
	@Dict(dicCode = "dept")
    @Schema(description = "部门（如计算机学院教学办）")
    private java.lang.String dept;
	/**授课科目（如Java程序设计）*/
	@Excel(name = "授课科目（如Java程序设计）", width = 15, dicCode = "subject")
	@Dict(dicCode = "subject")
    @Schema(description = "授课科目（如Java程序设计）")
    private java.lang.String subject;
	/**职称（如讲师/副教授）*/
	@Excel(name = "职称（如讲师/副教授）", width = 15)
    @Schema(description = "职称（如讲师/副教授）")
    private java.lang.String title;
	/**身份证号（可选AES加密）*/
	@Excel(name = "身份证号（可选AES加密）", width = 15)
    @Schema(description = "身份证号（可选AES加密）")
    private java.lang.String idCard;
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
