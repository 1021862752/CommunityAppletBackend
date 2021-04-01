package org.jeecg.modules.demo.wechat.service;

import org.jeecg.modules.demo.wechat.entity.WechatLoginRequest;

import java.util.Map;

public interface WechatService {
    Map<String, Object> getUserInfoMap(WechatLoginRequest loginRequest) throws Exception;
    Map<String, Object> getUserPhoneInfo(WechatLoginRequest loginRequest) throws Exception;
}
