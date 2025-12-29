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
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2025-12-28
 * @Version: V1.0
 */
@Data
@TableName("edu_student_score")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="学生成绩表")
public class EduStudentScore implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private java.lang.String id;
	/**创建人*/
    @Schema(description = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private java.lang.String sysOrgCode;
	/**选课id*/
	@Excel(name = "选课id", width = 15)
    @Schema(description = "选课id")
    private java.lang.String selectId;
	/**学生id*/
	@Excel(name = "学生id", width = 15)
    @Schema(description = "学生id")
    private java.lang.String studentId;
	/**课程id*/
	@Excel(name = "课程id", width = 15)
    @Schema(description = "课程id")
    private java.lang.String courseId;
	/**学期编码*/
	@Excel(name = "学期编码", width = 15)
    @Schema(description = "学期编码")
    private java.lang.String termCode;
	/**教师id*/
	@Excel(name = "教师id", width = 15)
    @Schema(description = "教师id")
    private java.lang.String teacherId;
	/**平时成绩*/
	@Excel(name = "平时成绩", width = 15)
    @Schema(description = "平时成绩")
    private java.lang.Double usualScore;
	/**平时成绩占比*/
	@Excel(name = "平时成绩占比", width = 15)
    @Schema(description = "平时成绩占比")
    private java.lang.Double usualScorePersent;
	/**期中成绩*/
	@Excel(name = "期中成绩", width = 15)
    @Schema(description = "期中成绩")
    private java.lang.Double midScore;
	/**期中成绩占比*/
	@Excel(name = "期中成绩占比", width = 15)
    @Schema(description = "期中成绩占比")
    private java.lang.Double midScorePersent;
	/**期末成绩*/
	@Excel(name = "期末成绩", width = 15)
    @Schema(description = "期末成绩")
    private java.lang.Double finalScore;
	/**期末成绩占比*/
	@Excel(name = "期末成绩占比", width = 15)
    @Schema(description = "期末成绩占比")
    private java.lang.Double finalScorePersent;
	/**综合成绩*/
	@Excel(name = "综合成绩", width = 15)
    @Schema(description = "综合成绩")
    private java.lang.Double totalScore;
	/**绩点*/
	@Excel(name = "绩点", width = 15)
    @Schema(description = "绩点")
    private java.lang.Double gradePoint;
	/**成绩状态*/
	@Excel(name = "成绩状态", width = 15, dicCode = "scorestatus")
	@Dict(dicCode = "scorestatus")
    @Schema(description = "成绩状态")
    private java.lang.String scoreStatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private java.lang.String remark;
}
