package org.jeecg.modules.demo.crouse.entity;

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
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2025-12-25
 * @Version: V1.0
 */
@Data
@TableName("edu_teacher")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="教师信息表")
public class EduTeacher implements Serializable {
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
	/**关联用户ID*/
	@Excel(name = "关联用户ID", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Schema(description = "关联用户ID")
    private java.lang.String userId;
	/**工号*/
	@Excel(name = "工号", width = 15)
    @Schema(description = "工号")
    private java.lang.String teacherNo;
	/**所属学院*/
	@Excel(name = "所属学院", width = 15, dicCode = "COLLEGE")
	@Dict(dicCode = "COLLEGE")
    @Schema(description = "所属学院")
    private java.lang.String college;
	/**教研室*/
	@Excel(name = "教研室", width = 15, dicCode = "DEPARTMENT")
	@Dict(dicCode = "DEPARTMENT")
    @Schema(description = "教研室")
    private java.lang.String department;
	/**职称*/
	@Excel(name = "职称", width = 15, dicCode = "TITLE")
	@Dict(dicCode = "TITLE")
    @Schema(description = "职称")
    private java.lang.String title;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    @Schema(description = "身份证号")
    private java.lang.String teacherIdCard;
}
