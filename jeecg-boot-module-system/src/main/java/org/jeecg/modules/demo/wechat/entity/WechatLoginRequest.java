package org.jeecg.modules.demo.wechat.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class WechatLoginRequest {
    @ApiModelProperty(value = "微信code", required = true)
    private String code;
    @ApiModelProperty(value = "用户非敏感字段")
    private String rawData;
    @ApiModelProperty(value = "签名")
    private String signature;
    @ApiModelProperty(value = "用户敏感字段")
    private String encryptedData;
    @ApiModelProperty(value = "解密向量")
    private String iv;
}