package org.jeecg.modules.demo.volunteeractivity.vo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 志愿活动
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
@Data
@ApiModel(value="md_volunteer_activityPage对象", description="志愿活动")
public class MdVolunteerActivityPage {
	
	/**主键*/
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
	/**活动类型*/
	@Excel(name = "活动类型", width = 15)
	@ApiModelProperty(value = "活动类型")
	private java.lang.String vaType;
	/**活动标题*/
	@Excel(name = "活动标题", width = 15)
	@ApiModelProperty(value = "活动标题")
	private java.lang.String vaTitle;
	/**活动城市*/
	@Excel(name = "活动城市", width = 15)
	@ApiModelProperty(value = "活动城市")
	private java.lang.String vaCity;
	/**活动地区*/
	@Excel(name = "活动地区", width = 15)
	@ApiModelProperty(value = "活动地区")
	private java.lang.String vaRegion;
	/**活动详细地址*/
	@Excel(name = "活动详细地址", width = 15)
	@ApiModelProperty(value = "活动详细地址")
	private java.lang.String vaAddress;
	/**
	 * 活动详情
	 */
	@Excel(name = "活动详情", width = 15)
	private transient java.lang.String vaDetailsString;

	private byte[] vaDetails;

	public byte[] getVaDetails() {
		if (vaDetailsString == null) {
			return null;
		}
		try {
			return vaDetailsString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getVaDetailsString() {
		if (vaDetails == null || vaDetails.length == 0) {
			return "";
		}
		try {
			return new String(vaDetails, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**活动名额*/
	@Excel(name = "活动名额", width = 15)
	@ApiModelProperty(value = "活动名额")
	private java.lang.Integer vaQuota;
	/**活动积分*/
	@Excel(name = "活动积分", width = 15)
	@ApiModelProperty(value = "活动积分")
	private java.lang.Integer vaIntegral;
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
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private java.lang.String vaStatus;
	/**参与人*/
	@Excel(name = "参与人", width = 15)
	@ApiModelProperty(value = "参与人")
	private java.lang.String vaParticipants;
	/**二维码*/
	@Excel(name = "二维码", width = 15)
	@ApiModelProperty(value = "二维码")
	private java.lang.String vaQrCode;
	
	@ExcelCollection(name="活动时间域信息")
	@ApiModelProperty(value = "活动时间域信息")
	private List<MdActivityTimeDetail> mdActivityTimeDetailList;
	
}
