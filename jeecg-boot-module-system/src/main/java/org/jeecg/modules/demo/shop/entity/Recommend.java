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
 * @Description: 推荐管理
 * @Author: jeecg-boot
 * @Date:   2020-07-25
 * @Version: V1.0
 */
@Data
@TableName("md_recommend")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_recommend对象", description="推荐管理")
public class Recommend {
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**推荐名称*/
	@Excel(name = "推荐名称", width = 15)
    @ApiModelProperty(value = "推荐名称")
	private java.lang.String recommendName;
	/**推荐图片地址*/
	@Excel(name = "推荐图片地址", width = 15)
    @ApiModelProperty(value = "推荐图片地址")
	private java.lang.String imgUrl;
	/**推荐商品*/
	@Excel(name = "推荐商品", width = 15)
    @ApiModelProperty(value = "推荐商品")
	private java.lang.String recommendGoods;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**排序id*/
	@Excel(name = "排序id", width = 15)
    @ApiModelProperty(value = "排序id")
	private java.lang.Integer sortId;
}
