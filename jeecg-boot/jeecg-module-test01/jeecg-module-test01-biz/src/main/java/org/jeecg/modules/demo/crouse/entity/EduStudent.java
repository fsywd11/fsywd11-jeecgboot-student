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
 * @Description: edu_student
 * @Author: jeecg-boot
 * @Date:   2026-01-04
 * @Version: V1.0
 */
@Data
@TableName("edu_student")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="edu_student")
public class EduStudent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
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
    /**学号*/
    @Excel(name = "学号", width = 15)
    @Schema(description = "学号")
    private java.lang.String studentNo;
    /**学院*/
    @Excel(name = "学院", width = 15, dicCode = "college")
    @Dict(dicCode = "college")
    @Schema(description = "学院")
    private java.lang.String college;
    /**专业*/
    @Excel(name = "专业", width = 15, dicCode = "major")
    @Dict(dicCode = "major")
    @Schema(description = "专业")
    private java.lang.String major;
    /**年级*/
    @Excel(name = "年级", width = 15, dicCode = "grade")
    @Dict(dicCode = "grade")
    @Schema(description = "年级")
    private java.lang.String grade;
    /**班级*/
    @Excel(name = "班级", width = 15)
    @Schema(description = "班级")
    private java.lang.String className;
    /**身份证号*/
    @Excel(name = "身份证号", width = 15)
    @Schema(description = "身份证号")
    private java.lang.String studentIdCard;
}
