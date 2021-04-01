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
 * @Description: 评价管理
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
@Data
@TableName("md_evaluate_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_evaluate_info对象", description="评价管理")
public class EvaluateInfo {
    
	/**唯一标识*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "唯一标识")
	private java.lang.String id;
	/**家政服务订单id*/
	@Excel(name = "家政服务订单id", width = 15)
    @ApiModelProperty(value = "家政服务订单id")
	private java.lang.String serviceOrderId;
	/**家政服务类型*/
	@Excel(name = "家政服务类型", width = 15)
    @ApiModelProperty(value = "家政服务类型")
	private java.lang.String commodityName;
	/**家政服务id*/
	@Excel(name = "家政服务id", width = 15)
    @ApiModelProperty(value = "家政服务id")
	private java.lang.String commodityId;
	/**客户id*/
	@Excel(name = "客户id", width = 15)
    @ApiModelProperty(value = "客户id")
	private java.lang.String customerId;
	/**客户姓名*/
	@Excel(name = "客户姓名", width = 15)
    @ApiModelProperty(value = "客户姓名")
	private java.lang.String customerName;
	/**评价*/
	@Excel(name = "评价", width = 15)
    @ApiModelProperty(value = "评价")
	private java.lang.String evaluate;
	/**头像地址*/
	@Excel(name = "头像地址", width = 15)
    @ApiModelProperty(value = "头像地址")
	private java.lang.String iconurl;
	/**评价时间*/
	@Excel(name = "评价时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "评价时间")
	private java.util.Date createTime;
}
