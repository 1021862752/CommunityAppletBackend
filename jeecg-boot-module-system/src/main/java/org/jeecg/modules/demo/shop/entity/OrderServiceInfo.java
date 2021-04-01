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
 * @Description: 预约项目列表
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("md_order_service_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_order_service_info对象", description="预约项目列表")
public class OrderServiceInfo {
    
	/**唯一标识*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "唯一标识")
	private java.lang.String id;
	/**商品分类*/
	@Excel(name = "商品分类", width = 15)
    @ApiModelProperty(value = "商品分类")
	private java.lang.String commodityType;
	/**预约项目*/
	@Excel(name = "预约项目", width = 15)
    @ApiModelProperty(value = "预约项目")
	private java.lang.String commodityName;
	/**商品介绍*/
	@Excel(name = "商品介绍", width = 15)
    @ApiModelProperty(value = "商品介绍")
	private java.lang.String productIntroduction;
	/**商品详情*/
	@Excel(name = "商品详情", width = 15)
    @ApiModelProperty(value = "商品详情")
	private java.lang.String productDetails;
	/**商品总数*/
	@Excel(name = "商品总数", width = 15)
    @ApiModelProperty(value = "商品总数")
	private java.lang.Integer productCount;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
	private java.lang.String contactInfo;
	/**视频地址*/
	@Excel(name = "视频地址", width = 15)
    @ApiModelProperty(value = "视频地址")
	private java.lang.String vedioUri;
	/**图片地址数组[]*/
	@Excel(name = "图片地址数组[]", width = 15)
    @ApiModelProperty(value = "图片地址数组[]")
	private java.lang.String imgList;
	/**发布状态 1：发布 0：草稿*/
	@Excel(name = "发布状态 1：发布 0：草稿", width = 15)
    @ApiModelProperty(value = "发布状态 1：发布 0：草稿")
	private java.lang.Integer status;
}
