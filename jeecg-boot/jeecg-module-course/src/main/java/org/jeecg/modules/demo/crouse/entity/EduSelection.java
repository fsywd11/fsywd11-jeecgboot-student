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
 * @Description: edu_selection
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("edu_selection")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="edu_selection")
public class EduSelection implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联campus_student.id（选课学生ID）*/
	@Excel(name = "关联campus_student.id（选课学生ID）", width = 15, dictTable = "campus_student", dicCode = "id", dicText = "student_no")
	@Dict(dictTable = "campus_student", dicCode = "id", dicText = "student_no")
    @Schema(description = "关联campus_student.id（选课学生ID）")
    private java.lang.Long studentId;
	/**关联edu_course.id（课程ID）*/
	@Excel(name = "关联edu_course.id（课程ID）", width = 15, dictTable = "edu_course", dicCode = "id", dicText = "course_code")
	@Dict(dictTable = "edu_course", dicCode = "id", dicText = "course_code")
    @Schema(description = "关联edu_course.id（课程ID）")
    private java.lang.Long courseId;
	/**选课时间*/
	@Excel(name = "选课时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "选课时间")
    private java.util.Date selectTime;
	/**是否退选：0未退选/1已退选*/
	@Excel(name = "是否退选：0未退选/1已退选", width = 15, dicCode = "SELECT_STATUS")
	@Dict(dicCode = "SELECT_STATUS")
    @Schema(description = "是否退选：0未退选/1已退选")
    private java.lang.Integer isCancel;
	/**退选时间（未退选则为NULL）*/
	@Excel(name = "退选时间（未退选则为NULL）", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "退选时间（未退选则为NULL）")
    private java.util.Date cancelTime;
	/**选课年份（用于分表，如2024）*/
	@Excel(name = "选课年份（用于分表，如2024）", width = 15)
    @Schema(description = "选课年份（用于分表，如2024）")
    private java.lang.Integer selectYear;
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
