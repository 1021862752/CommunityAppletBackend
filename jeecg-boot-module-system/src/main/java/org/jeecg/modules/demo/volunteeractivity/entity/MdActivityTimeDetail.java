package org.jeecg.modules.demo.volunteeractivity.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 活动时间域信息
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
@ApiModel(value="md_volunteer_activity对象", description="志愿活动")
@Data
@TableName("md_activity_time_detail")
public class MdActivityTimeDetail implements Serializable {
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
	/**活动开始时间*/
	@Excel(name = "活动开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "活动开始时间")
	private java.util.Date vaTimeStart;
	/**活动结束时间*/
	@Excel(name = "活动结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "活动结束时间")
	private java.util.Date vaTimeEnd;
	/**活动积分*/
	@Excel(name = "活动积分", width = 15)
	@ApiModelProperty(value = "活动积分")
	private java.lang.String vaIntegral;
	/**是否报名*/
	@Excel(name = "是否报名", width = 15)
	@ApiModelProperty(value = "是否报名")
	private java.lang.String vaIsSign;
	/**活动名额*/
	@Excel(name = "活动名额", width = 15)
	@ApiModelProperty(value = "活动名额")
	private java.lang.Integer vaQuota;
	/**外键*/
	@ApiModelProperty(value = "外键")
	private java.lang.String alFkId;
}
