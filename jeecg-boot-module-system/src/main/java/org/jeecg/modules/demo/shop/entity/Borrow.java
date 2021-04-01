package org.jeecg.modules.demo.shop.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 我的借用
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
@TableName("md_borrow")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_borrow对象", description="我的借用")
public class Borrow {
    
	/**借用编号*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "借用编号")
	private java.lang.String id;
	/**借用商品*/
	@Excel(name = "借用商品", width = 15)
    @ApiModelProperty(value = "借用商品")
	private java.lang.String goodsName;
	/**客户姓名*/
	@Excel(name = "客户姓名", width = 15)
    @ApiModelProperty(value = "客户姓名")
	private java.lang.String customerName;
	/**客户id*/
	@Excel(name = "客户id", width = 15)
    @ApiModelProperty(value = "客户id")
	private java.lang.String customerId;
	/**客户地址*/
	@Excel(name = "客户地址", width = 15)
    @ApiModelProperty(value = "客户地址")
	private java.lang.String customerAddress;
	/**客户电话*/
	@Excel(name = "客户电话", width = 15)
    @ApiModelProperty(value = "客户电话")
	private java.lang.String customerPhone;
	/**借用时间*/
	@Excel(name = "借用时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "借用时间")
	private java.util.Date borrowTime;
	/**归还时间*/
	@Excel(name = "归还时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "归还时间")
	private java.util.Date returnTime;
	/**订单状态 0:待归还 1:已归还  2：借用超时*/
	@Excel(name = "订单状态 0:待归还 1:已归还  2：借用超时", width = 15)
    @ApiModelProperty(value = "订单状态 0:待归还 1:已归还  2：借用超时")
	private java.lang.Integer orderState;
	/**免费使用时间（单位：小时）*/
	@Excel(name = "免费使用时间（单位：小时）", width = 15)
    @ApiModelProperty(value = "免费使用时间（单位：小时）")
	private java.lang.Integer freeTime;
	/**下单时间*/
	@Excel(name = "下单时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下单时间")
	private java.util.Date orderTime;
	/**备注信息*/
	@Excel(name = "备注信息", width = 15)
    @ApiModelProperty(value = "备注信息")
	private java.lang.String remark;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
}
