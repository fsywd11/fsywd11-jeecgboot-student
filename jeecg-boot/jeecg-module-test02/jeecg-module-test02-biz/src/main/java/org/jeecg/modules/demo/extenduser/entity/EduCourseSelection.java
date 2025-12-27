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
 * @Description: 选课记录表
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V1.0
 */
@Data
@TableName("edu_course_selection")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="选课记录表")
public class EduCourseSelection implements Serializable {
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
	/**学生ID*/
	@Excel(name = "学生ID", width = 15)
    @Schema(description = "学生ID")
    private String studentId;
	/**学号*/
	@Excel(name = "学号", width = 15)
    @Schema(description = "学号")
    private String studentNo;
	/**课程ID*/
	@Excel(name = "课程ID", width = 15)
    @Schema(description = "课程ID")
    private String courseId;
	/**课程编号*/
	@Excel(name = "课程编号", width = 15)
    @Schema(description = "课程编号")
    private String courseNo;
	/**课程名称*/
	@Excel(name = "课程名称", width = 15)
    @Schema(description = "课程名称")
    private String courseName;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "SELECTSTATUS")
	@Dict(dicCode = "SELECTSTATUS")
    @Schema(description = "状态")
    private String status;
}
