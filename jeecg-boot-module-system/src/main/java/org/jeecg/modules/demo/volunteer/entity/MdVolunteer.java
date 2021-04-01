package org.jeecg.modules.demo.volunteer.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 志愿者
 * @Author: jeecg-boot
 * @Date:   2020-05-27
 * @Version: V1.0
 */
@Data
@TableName("md_volunteer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="md_volunteer对象", description="志愿者")
public class MdVolunteer implements Serializable {
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
	/**志愿者编号*/
	@Excel(name = "志愿者编号", width = 15)
    @ApiModelProperty(value = "志愿者编号")
    private java.lang.String vtCode;
	/**志愿者姓名*/
	@Excel(name = "志愿者姓名", width = 15)
    @ApiModelProperty(value = "志愿者姓名")
    private java.lang.String vtName;
	/**志愿者手机*/
	@Excel(name = "志愿者手机", width = 15)
    @ApiModelProperty(value = "志愿者手机")
    private java.lang.String vtPhone;
	/**志愿者openId*/
	@Excel(name = "志愿者openId", width = 15)
    @ApiModelProperty(value = "志愿者openId")
    private java.lang.String vtOpenId;
    /**身份证*/
    @Excel(name = "身份证", width = 15)
    @ApiModelProperty(value = "身份证")
    private java.lang.String identityId;

    /**积分*/
    @Excel(name = "积分", width = 15)
    @ApiModelProperty(value = "积分")
    private java.lang.Integer vtIntegral;
}
