package org.jeecg.modules.demo.extenduser.entity;

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
 * @Description: campus_user_extend
 * @Author: jeecg-boot
 * @Date:   2025-12-23
 * @Version: V1.0
 */
@Data
@TableName("campus_user_extend")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="campus_user_extend")
public class CampusUserExtend implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private java.lang.Long id;
	/**关联sys_user.id（用户主键）*/
	@Excel(name = "关联sys_user.id（用户主键）", width = 15, dictTable = "sys_user", dicText = "username,realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "username,realname", dicCode = "id")
    @Schema(description = "关联sys_user.id（用户主键）")
    private java.lang.String userId;
	/**角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）*/
	@Excel(name = "角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）", width = 15, dicCode = "ROLE_TYPES")
	@Dict(dicCode = "ROLE_TYPES")
    @Schema(description = "角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）")
    private java.lang.Integer roleType;
	/**联系电话（与sys_user.phone区分，可选AES加密）*/
	@Excel(name = "联系电话（与sys_user.phone区分，可选AES加密）", width = 15)
    @Schema(description = "联系电话（与sys_user.phone区分，可选AES加密）")
    private java.lang.String phone;
	/**第三方登录-微信openid（对应sys_user.third_id）*/
	@Excel(name = "第三方登录-微信openid（对应sys_user.third_id）", width = 15)
    @Schema(description = "第三方登录-微信openid（对应sys_user.third_id）")
    private java.lang.String wechatOpenid;
	/**第三方登录-githubopenid*/
	@Excel(name = "第三方登录-githubopenid", width = 15)
    @Schema(description = "第三方登录-githubopenid")
    private java.lang.String dingtalkOpenid;
	/**第三方登录类型（对应sys_user.third_type）*/
	@Excel(name = "第三方登录类型（对应sys_user.third_type）", width = 15)
    @Schema(description = "第三方登录类型（对应sys_user.third_type）")
    private java.lang.String thirdType;
	/**状态：1正常/2冻结（参考sys_user.status）*/
	@Excel(name = "状态：1正常/2冻结（参考sys_user.status）", width = 15)
    @Schema(description = "状态：1正常/2冻结（参考sys_user.status）")
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
	/**删除状态：0正常/1已删除（参考sys_user.del_flag）*/
	@Excel(name = "删除状态：0正常/1已删除（参考sys_user.del_flag）", width = 15)
    @Schema(description = "删除状态：0正常/1已删除（参考sys_user.del_flag）")
    @TableLogic
    private java.lang.Integer delFlag;
}
