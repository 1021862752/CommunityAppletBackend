package org.jeecg.modules.demo.wechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawDataDO {
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String city;
    private String country;
    private String province;
}