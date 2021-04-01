package org.jeecg.modules.demo.appeal.vo;

import java.util.List;
import org.jeecg.modules.demo.appeal.entity.MdAppealInfo;
import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
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
 * @Description: 诉求管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
@Data
@ApiModel(value="md_appeal_infoPage对象", description="诉求管理")
public class MdAppealInfoPage {
	
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
	/**诉求标题*/
	@Excel(name = "诉求标题", width = 15)
	@ApiModelProperty(value = "诉求标题")
	private java.lang.String appealTitle;
	/**诉求姓名*/
	@Excel(name = "诉求姓名", width = 15)
	@ApiModelProperty(value = "诉求姓名")
	private java.lang.String appealUserId;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
	@ApiModelProperty(value = "联系方式")
	private java.lang.String appealContact;
	/**诉求内容*/
	@Excel(name = "诉求内容", width = 15)
	@ApiModelProperty(value = "诉求内容")
	private java.lang.String appealContent;
	/**诉求地址*/
	@Excel(name = "诉求地址", width = 15)
	@ApiModelProperty(value = "诉求地址")
	private java.lang.String appealAddr;
	/**发布时间*/
	@Excel(name = "发布时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "发布时间")
	private java.util.Date appealReleaseTime;
	/**回复状态*/
	@Excel(name = "回复状态", width = 15)
	@ApiModelProperty(value = "回复状态")
	private java.lang.String appealReplyStatus;
	/**回复内容*/
	@Excel(name = "回复内容", width = 15)
	@ApiModelProperty(value = "回复内容")
	private java.lang.String appealReplyContent;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
	@ApiModelProperty(value = "用户名")
	private java.lang.String appealName;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
	@ApiModelProperty(value = "手机号")
	private java.lang.String appealPhone;
	
	@ExcelCollection(name="诉求图片管理")
	@ApiModelProperty(value = "诉求图片管理")
	private List<MdPictureInfo> mdPictureInfoList;
	
}
