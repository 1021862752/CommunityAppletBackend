package org.jeecg.modules.demo.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jeecg.modules.demo.wechat.entity.RawDataDO;
import org.jeecg.modules.demo.wechat.entity.WechatLoginRequest;
import org.jeecg.modules.demo.wechat.entity.WechatUserDO;
import org.jeecg.modules.demo.wechat.service.WechatService;
import org.jeecg.modules.demo.wechat.utils.HttpClientUtils;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String APPID = "wx3d10664dc6071909";
    private static final String SECRET = "6dfd82a883482a19966c341ba0fa8cf3";
    //private static final String = "authorization_code";
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public Map<String, Object> getUserInfoMap(WechatLoginRequest loginRequest) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<>();
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());

        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        WechatUserDO insertOrUpdateDO = buildWechatUserAuthInfoDO(loginRequest, sessionKey, openId);

        // 根据code保存openId和sessionKey
        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openId);
        sessionObj.put("sessionKey", sessionKey);

        // 根据openid查询用户，这里的查询service自己写，就不贴出来了
        SysUser user = userMapper.getUserByOpid(openId);
        if (user == null) {
            user = new SysUser();
            user.setId(openId);
            user.setStatus(1);
            user.setSex(0);
            user.setDelFlag("1");
            user.setIdentity(1);
            sysUserService.addUser(user);
        }
        userInfoMap.put("timestamp",getToken());
        userInfoMap.put("success",true);
        userInfoMap.put("message","登录成功");
        userInfoMap.put("code","200");
        userInfoMap.put("result",null);
        userInfoMap.put("userId",openId);
        return userInfoMap;
    }

    @Override
    public Map<String, Object> getUserPhoneInfo(WechatLoginRequest loginRequest) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<>();
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());
        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
        WechatUserDO insertOrUpdateDO = buildWechatUserAuthInfoDO(loginRequest, sessionKey, openId);
        // 根据openid查询用户，这里的查询service自己写，就不贴出来了
        SysUser user = userMapper.getUserByOpid(openId);
        if (user == null) {
            user = new SysUser();
            user.setId(openId);
            user.setUsername(insertOrUpdateDO.getMobile());
            user.setPhone(insertOrUpdateDO.getMobile());
            user.setStatus(1);
            user.setSex(0);
            user.setDelFlag("0");
            user.setIdentity(1);
            sysUserService.addUser(user);
        }else{
            user.setUsername(insertOrUpdateDO.getMobile());
            user.setPhone(insertOrUpdateDO.getMobile());
            user.setDelFlag("0");
            sysUserService.updateUserByOpid(user.getId(),user);
            sysUserService.editUserById(user);
        }
        userInfoMap.put("timestamp",getToken());
        userInfoMap.put("success",true);
        userInfoMap.put("message","获取成功");
        userInfoMap.put("code","200");
        userInfoMap.put("result",null);
        userInfoMap.put("userId",openId);
        userInfoMap.put("phone",insertOrUpdateDO.getMobile());
        return userInfoMap;
    }

    private JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId
        requestUrlParam.put("appid", APPID);
        // 小程序secre
        requestUrlParam.put("secret", SECRET);
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", "refresh_token");

        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtils.doPost(REQUEST_URL, requestUrlParam);
        return JSON.parseObject(result);
    }

    private WechatUserDO buildWechatUserAuthInfoDO(WechatLoginRequest loginRequest, String sessionKey, String openId) {
        WechatUserDO wechatUserDO = new WechatUserDO();
        wechatUserDO.setOpenId(openId);

        if (loginRequest.getRawData() != null && !"".equals(loginRequest.getRawData())) {
            RawDataDO rawDataDO = JSON.parseObject(loginRequest.getRawData(), RawDataDO.class);
            wechatUserDO.setNickname(rawDataDO.getNickName());
            wechatUserDO.setAvatarUrl(rawDataDO.getAvatarUrl());
            wechatUserDO.setGender(rawDataDO.getGender());
            wechatUserDO.setCity(rawDataDO.getCity());
            wechatUserDO.setCountry(rawDataDO.getCountry());
            wechatUserDO.setProvince(rawDataDO.getProvince());
        }

        // 解密加密信息，获取unionID
        if (loginRequest.getEncryptedData() != null) {
            JSONObject encryptedData = getEncryptedData(loginRequest.getEncryptedData(), sessionKey, loginRequest.getIv());
            if (encryptedData != null) {
                String unionId = encryptedData.getString("unionId");
                wechatUserDO.setUnionId(unionId);
                wechatUserDO.setMobile(encryptedData.getString("phoneNumber"));
            }
        }

        return wechatUserDO;
    }

    private JSONObject getEncryptedData(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.这个if中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private String getToken() throws Exception {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}