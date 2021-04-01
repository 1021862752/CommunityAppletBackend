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
 * @Description: 预约订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Data
@TableName("md_booking_order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_booking_order对象", description="预约订单管理")
public class BookingOrder {
    
	/**预约id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "预约id")
	private java.lang.String id;
	/**预约服务*/
	@Excel(name = "预约服务", width = 15)
    @ApiModelProperty(value = "预约服务")
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
	/**是否住家：0：不住家 1：住家*/
	@Excel(name = "是否住家：0：不住家 1：住家", width = 15)
    @ApiModelProperty(value = "是否住家：0：不住家 1：住家")
	private java.lang.Integer liveHome;
	/**雇佣方式：1 长期 */
	@Excel(name = "雇佣方式：1 长期 ", width = 15)
    @ApiModelProperty(value = "雇佣方式：1 长期 ")
	private java.lang.Integer employType;
	/**工作年限 (-1为长期)*/
	@Excel(name = "工作年限 (-1为长期)", width = 15)
    @ApiModelProperty(value = "工作年限 (-1为长期)")
	private java.lang.Integer workingYears;
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
