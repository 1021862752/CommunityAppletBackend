package org.jeecg.modules.demo.wechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatUserDO {
    private Integer id;

    private String token;

    private String nickname;

    private String avatarUrl;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String mobile;

    private String openId;

    private String unionId;

    private String createdAt;

    private String updatedAt;
}