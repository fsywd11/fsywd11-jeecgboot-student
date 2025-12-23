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
 * @Description: edu_score
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("edu_score")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="edu_score")
public class EduScore implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联edu_selection.id（选课记录ID）*/
	@Excel(name = "关联edu_selection.id（选课记录ID）", width = 15, dictTable = "edu_selection", dicCode = "id", dicText = "student_id")
	@Dict(dictTable = "edu_selection", dicCode = "id", dicText = "student_id")
    @Schema(description = "关联edu_selection.id（选课记录ID）")
    private java.lang.Long selectionId;
	/**分数（如85.50/60.00，NULL表示未录入）*/
	@Excel(name = "分数（如85.50/60.00，NULL表示未录入）", width = 15)
    @Schema(description = "分数（如85.50/60.00，NULL表示未录入）")
    private java.math.BigDecimal score;
	/**成绩等级（如优秀/良好/及格/不及格）*/
	@Excel(name = "成绩等级（如优秀/良好/及格/不及格）", width = 15, dicCode = "score")
	@Dict(dicCode = "score")
    @Schema(description = "成绩等级（如优秀/良好/及格/不及格）")
    private java.lang.String scoreLevel;
	/**成绩录入时间*/
	@Excel(name = "成绩录入时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "成绩录入时间")
    private java.util.Date scoreTime;
	/**关联campus_teacher.id（录入教师ID）*/
	@Excel(name = "关联campus_teacher.id（录入教师ID）", width = 15, dictTable = "campus_teacher", dicCode = "id", dicText = "teacher_no")
	@Dict(dictTable = "campus_teacher", dicCode = "id", dicText = "teacher_no")
    @Schema(description = "关联campus_teacher.id（录入教师ID）")
    private java.lang.Long teacherId;
	/**备注（如缺考/缓考）*/
	@Excel(name = "备注（如缺考/缓考）", width = 15)
    @Schema(description = "备注（如缺考/缓考）")
    private java.lang.String remark;
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
