package org.jeecg.modules.demo.clapwill.entity;

import java.io.Serializable;
import java.util.Date;
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

/**
 * @Description: 随手拍管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
@ApiModel(value="md_clap_will_info对象", description="随手拍管理")
@Data
@TableName("md_clap_will_info")
public class MdClapWillInfo implements Serializable {
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
	/**随手拍标题*/
	@Excel(name = "随手拍标题", width = 15)
    @ApiModelProperty(value = "随手拍标题")
    private java.lang.String cwTitle;
	/**随手拍姓名*/
	@Excel(name = "随手拍姓名", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
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
    /**回复内容*/
    @Excel(name = "提交人", width = 15)
    @ApiModelProperty(value = "提交人")
    private java.lang.String cwName;
}
