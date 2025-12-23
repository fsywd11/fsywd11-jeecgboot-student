package org.jeecg.modules.demo.crouse.entity;

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
 * @Description: edu_course
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("edu_course")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="edu_course")
public class EduCourse implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**课程编码（唯一）*/
	@Excel(name = "课程编码（唯一）", width = 15)
    @Schema(description = "课程编码（唯一）")
    private java.lang.String courseCode;
	/**课程名称（如Java程序设计）*/
	@Excel(name = "课程名称（如Java程序设计）", width = 15)
    @Schema(description = "课程名称（如Java程序设计）")
    private java.lang.String courseName;
	/**关联campus_teacher.id（授课教师ID）*/
	@Excel(name = "关联campus_teacher.id（授课教师ID）", width = 15, dictTable = "campus_teacher", dicCode = "id", dicText = "teacher_no")
	@Dict(dictTable = "campus_teacher", dicCode = "id", dicText = "teacher_no")
    @Schema(description = "关联campus_teacher.id（授课教师ID）")
    private java.lang.Long teacherId;
	/**学分（如2.0/3.5）*/
	@Excel(name = "学分（如2.0/3.5）", width = 15)
    @Schema(description = "学分（如2.0/3.5）")
    private java.math.BigDecimal credit;
	/**最大选课人数（默认100人）*/
	@Excel(name = "最大选课人数（默认100人）", width = 15)
    @Schema(description = "最大选课人数（默认100人）")
    private java.lang.Integer maxStudent;
	/**当前选课人数（初始0）*/
	@Excel(name = "当前选课人数（初始0）", width = 15)
    @Schema(description = "当前选课人数（初始0）")
    private java.lang.Integer currentStudent;
	/**上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）*/
	@Excel(name = "上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）", width = 15)
    @Schema(description = "上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）")
    private java.lang.String classTime;
	/**上课地点（如图书馆302教室）*/
	@Excel(name = "上课地点（如图书馆302教室）", width = 15)
    @Schema(description = "上课地点（如图书馆302教室）")
    private java.lang.String classPlace;
	/**课程描述（可选）*/
	@Excel(name = "课程描述（可选）", width = 15)
    @Schema(description = "课程描述（可选）")
    private java.lang.String courseDesc;
	/**课程状态：1正常/2已结束/3未启用（参考sys_user.status）*/
	@Excel(name = "课程状态：1正常/2已结束/3未启用（参考sys_user.status）", width = 15, dicCode = "COURSE_STATUS")
	@Dict(dicCode = "COURSE_STATUS")
    @Schema(description = "课程状态：1正常/2已结束/3未启用（参考sys_user.status）")
    private java.lang.Integer status;
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
