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
 * @Description: 商城轮播图管理
 * @Author: jeecg-boot
 * @Date:   2020-07-27
 * @Version: V1.0
 */
@Data
@TableName("md_shop_rc_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_shop_rc_info对象", description="商城轮播图管理")
public class ShopRcInfo {
    
	/**商城轮播图id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "商城轮播图id")
	private java.lang.String id;
	/**商城类型*/
	@Excel(name = "商城类型", width = 15)
    @ApiModelProperty(value = "商城类型")
	private java.lang.String goodsType;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
    @ApiModelProperty(value = "商品id")
	private java.lang.String goodsId;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
	private java.lang.String imgUrl;
}
