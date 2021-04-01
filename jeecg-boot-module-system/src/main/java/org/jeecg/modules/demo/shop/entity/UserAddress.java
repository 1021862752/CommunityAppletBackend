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
 * @Description: 用户地址管理
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("md_user_address")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="md_user_address对象", description="用户地址管理")
public class UserAddress {
    
	/**唯一标识*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "唯一标识")
	private java.lang.String id;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private java.lang.String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
	private java.lang.String username;
	/**性别：1：男  2：女*/
	@Excel(name = "性别：1：男  2：女", width = 15)
    @ApiModelProperty(value = "性别：1：男  2：女")
	private java.lang.Integer sex;
	/**电话号码*/
	@Excel(name = "电话号码", width = 15)
    @ApiModelProperty(value = "电话号码")
	private java.lang.String phoneNumber;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
	private java.lang.String address;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
	private java.lang.String detailAddress;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
}
