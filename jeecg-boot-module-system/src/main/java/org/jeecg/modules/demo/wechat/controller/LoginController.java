package org.jeecg.modules.demo.wechat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.demo.wechat.entity.WechatLoginRequest;
import org.jeecg.modules.demo.wechat.service.WechatService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
@Api(tags="微信")
@RestController("LoginController")
@RequestMapping(value = "/wechat/login")
@Slf4j
public class LoginController {
    @Resource
    WechatService wechatService;

    @AutoLog(value = "微信登入")
    @ApiOperation(value = "1.登入接口", httpMethod = "POST")
    @PostMapping("/save")
    public Map<String, Object> login(
            @Validated @RequestBody WechatLoginRequest loginRequest) throws Exception {

        Map<String, Object> userInfoMap = wechatService.getUserInfoMap(loginRequest);
        return userInfoMap;
    }
    @AutoLog(value = "获取手机号")
    @ApiOperation(value = "2.获取手机号", httpMethod = "POST")
    @PostMapping("/getPhone")
    public Map<String, Object> getPhone(
            @Validated @RequestBody WechatLoginRequest loginRequest) throws Exception {

        Map<String, Object> userInfoMap = wechatService.getUserPhoneInfo(loginRequest);
        return userInfoMap;
    }
}