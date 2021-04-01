package org.jeecg.modules.demo.clapwill.vo;

import java.util.List;
import org.jeecg.modules.demo.clapwill.entity.MdClapWillInfo;
import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
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
 * @Description: 随手拍管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
@Data
@ApiModel(value="md_clap_will_infoPage对象", description="随手拍管理")
public class MdClapWillInfoPage {
	
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
	/**随手拍标题*/
	@Excel(name = "随手拍标题", width = 15)
	@ApiModelProperty(value = "随手拍标题")
	private java.lang.String cwTitle;
	/**随手拍姓名*/
	@Excel(name = "随手拍姓名", width = 15)
	@ApiModelProperty(value = "随手拍姓名")
	private java.lang.String cwUserId;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
	@ApiModelProperty(value = "联系方式")
	private java.lang.String cwContact;
	/**随手拍内容*/
	@Excel(name = "随手拍内容", width = 15)
	@ApiModelProperty(value = "随手拍内容")
	private java.lang.String cwContent;
	/**随手拍地址*/
	@Excel(name = "随手拍地址", width = 15)
	@ApiModelProperty(value = "随手拍地址")
	private java.lang.String cwAddr;
	/**发布时间*/
	@Excel(name = "发布时间", width = 15)
	@ApiModelProperty(value = "发布时间")
	private java.lang.String cwReleaseTime;
	/**回复状态*/
	@Excel(name = "回复状态", width = 15)
	@ApiModelProperty(value = "回复状态")
	private java.lang.String cwReplyStatus;
	/**回复内容*/
	@Excel(name = "回复内容", width = 15)
	@ApiModelProperty(value = "回复内容")
	private java.lang.String cwReplyContent;
	/**提交人*/
	@Excel(name = "提交人", width = 15)
	@ApiModelProperty(value = "提交人")
	private java.lang.String cwName;

	@ExcelCollection(name="随手怕图片管理")
	@ApiModelProperty(value = "随手怕图片管理")
	private List<MdCwInfo> mdCwInfoList;
	
}
