package org.jeecg.modules.demo.extenduser.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2025-12-25
 * @Version: V1.0
 */
@Data
@TableName("edu_course")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="课程表")
public class EduCourse implements Serializable {
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
    private java.util.Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private String sysOrgCode;
	/**课程编号*/
	@Excel(name = "课程编号", width = 15)
    @Schema(description = "课程编号")
    private String courseNo;
	/**课程名称*/
	@Excel(name = "课程名称", width = 15)
    @Schema(description = "课程名称")
    private String courseName;
    /**所属学院*/
    @Excel(name = "开课学院", width = 15, dicCode = "college")
    @Dict(dicCode = "college")
    @Schema(description = "开课学院")
    private String college;
	/**学分*/
	@Excel(name = "学分", width = 15)
    @Schema(description = "学分")
    private String credit;
	/**课时*/
	@Excel(name = "课时", width = 15)
    @Schema(description = "课时")
    private String classHour;
	/**授课教师ID*/
	@Excel(name = "授课教师ID", width = 15)
    @Schema(description = "授课教师ID")
    private String teacherId;
	/**授课教师姓名*/
	@Excel(name = "授课教师姓名", width = 15)
    @Schema(description = "授课教师姓名")
    private String teacherName;
	/**上课时间*/
	@Excel(name = "上课时间", width = 15)
    @Schema(description = "上课时间")
    private String courseTime;
	/**上课教室*/
	@Excel(name = "上课教室", width = 15)
    @Schema(description = "上课教室")
    private String classroom;
	/**最大选课人数*/
	@Excel(name = "最大选课人数", width = 15)
    @Schema(description = "最大选课人数")
    private String maxStudent;
	/**当前选课人数*/
	@Excel(name = "当前选课人数", width = 15)
    @Schema(description = "当前选课人数")
    private String currentStudent;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "COURSE_STATUS")
	@Dict(dicCode = "COURSE_STATUS")
    @Schema(description = "状态")
    private String status;
}
