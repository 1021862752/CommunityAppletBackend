package org.jeecg.modules.demo.activitylog.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 活动参与记录
 * @Author: jeecg-boot
 * @Date:   2020-05-31
 * @Version: V1.0
 */
@Data
@TableName("md_activity_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="md_activity_log对象", description="活动参与记录")
public class MdActivityLog implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**活动ID*/
	@Excel(name = "活动ID", width = 15)
    @ApiModelProperty(value = "活动ID")
    private java.lang.String alId;
    /**活动ID*/
    @Excel(name = "活动明细ID", width = 15)
    @ApiModelProperty(value = "活动明细ID")
    private java.lang.String alDetailId;
	/**活动名称*/
	@Excel(name = "活动名称", width = 15)
    @ApiModelProperty(value = "活动名称")
    private java.lang.String alName;
	/**志愿者openId*/
	@Excel(name = "志愿者openId", width = 15)
    @ApiModelProperty(value = "志愿者openId")
    @Dict(dictTable ="sys_user",dicText = "realname",dicCode = "id")
    private java.lang.String alOpenId;
	/**志愿者编号*/
	@Excel(name = "志愿者编号", width = 15)
    @ApiModelProperty(value = "志愿者编号")
    private java.lang.String alCode;
	/**签到标识*/
	@Excel(name = "签到标识", width = 15)
    @ApiModelProperty(value = "签到标识")
    private java.lang.String alSignIn;
    /**活动开始时间*/
    @Excel(name = "活动开始时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "活动开始时间")
    private java.util.Date alTimeStart;
    /**活动结束时间*/
    @Excel(name = "活动结束时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "活动结束时间")
    private java.util.Date alTimeEnd;
    @Excel(name = "活动状态", width = 15)
    @ApiModelProperty(value = "活动状态")
    private java.lang.String activityStatus;
    @Excel(name = "活动类型", width = 150)
    @ApiModelProperty(value = "活动类型")
    private java.lang.String alType;
    @Excel(name = "活动地址", width = 150)
    @ApiModelProperty(value = "活动地址")
    private java.lang.String alAddress;
    @Excel(name = "身份证", width = 150)
    @ApiModelProperty(value = "身份证")
    @Dict(dictTable ="sys_user",dicText = "identity_id",dicCode = "id")
    private java.lang.String alIdentity;
    @Excel(name = "积分", width = 150)
    @ApiModelProperty(value = "积分")
    private java.lang.Integer alIntegral;
    @Excel(name = "手机号", width = 150)
    @ApiModelProperty(value = "手机号")
    private java.lang.String alPhone;
}
