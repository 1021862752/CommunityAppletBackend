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
 * @Description: 家政订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-19
 * @Version: V1.0
 */
@Data
@TableName("md_domestic_order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_domestic_order对象", description="家政订单管理")
public class DomesticOrder {
    
	/**订单编号*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "订单编号")
	private java.lang.String id;
	/**支付订单id*/
	@Excel(name = "支付订单id", width = 15)
    @ApiModelProperty(value = "支付订单id")
	private java.lang.String payOrderId;
	/**家政服务类型*/
	@Excel(name = "家政服务类型", width = 15)
    @ApiModelProperty(value = "家政服务类型")
	private java.lang.String goodsName;
	/**客户id*/
	@Excel(name = "客户id", width = 15)
    @ApiModelProperty(value = "客户id")
	private java.lang.String customerId;
	/**客户姓名*/
	@Excel(name = "客户姓名", width = 15)
    @ApiModelProperty(value = "客户姓名")
	private java.lang.String customerName;
	/**客户地址*/
	@Excel(name = "客户地址", width = 15)
    @ApiModelProperty(value = "客户地址")
	private java.lang.String customerAddress;
	/**客户电话*/
	@Excel(name = "客户电话", width = 15)
    @ApiModelProperty(value = "客户电话")
	private java.lang.String customerPhone;
	/**预约时间*/
	@Excel(name = "预约时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预约时间")
	private java.util.Date orderTime;
	/**订单状态  1：已支付 2：待支付 3:进行中 4:待评价 5:已完成 6:已取消*/
	@Excel(name = "订单状态  1：已支付 2：待支付 3:进行中 4:待评价 5:已完成 6:已取消", width = 15)
    @ApiModelProperty(value = "订单状态  1：已支付 2：待支付 3:进行中 4:待评价 5:已完成 6:已取消")
	private java.lang.Integer orderState;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
	private java.lang.Float cost;
	/**应收*/
	@Excel(name = "应收", width = 15)
    @ApiModelProperty(value = "应收")
	private java.lang.Float receivable;
	/**支付方式 1:微信*/
	@Excel(name = "支付方式 1:微信", width = 15)
    @ApiModelProperty(value = "支付方式 1:微信")
	private java.lang.Integer payType;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
	private java.lang.Integer cout;
	/**面积*/
	@Excel(name = "面积", width = 15)
    @ApiModelProperty(value = "面积")
	private java.lang.Integer area;
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
