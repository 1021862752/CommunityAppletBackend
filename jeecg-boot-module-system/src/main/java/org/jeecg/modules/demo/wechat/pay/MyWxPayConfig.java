package org.jeecg.modules.demo.wechat.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class MyWxPayConfig extends WXPayConfig {
    @Value("${vendor.wx.mp.app_id}")
    private String appID;
    @Value("${vendor.wx.pay.mch_id}")
    private String mchID;
    @Value("${vendor.wx.pay.key}")
    private String key;
    @Value("${vendor.wx.config.cert_path}")
    private String certPath;
    @Value("${vendor.wx.config.callback}")
    private String callback;

    private byte[] certData;
    private IWXPayDomain wxPayDomain = new WXPayDomain();

    @Override
    String getAppID() {
        return this.appID;
    }

    @Override
    String getMchID() {
        return this.mchID;
    }

    @Override
    String getKey() {
        return this.key;
    }

    @Override
    InputStream getCertStream() {
        File file = new File(certPath);
        InputStream certStream = null;
        try {
            certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return certStream;
        }
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return wxPayDomain;
    }

    /**
     * 对外暴露key
     * @return
     */
    public String pkey() {
        return this.key;
    }

    /**
     * 可以通过url携带参数
     * @param p1 需要携带的参数，例如a/bc/d，开头不要加/
     * @return
     */
    public String getCallback(String p1) {
        return this.callback + (this.callback.lastIndexOf("/") == this.callback.length() - 1 ? "" : "/") + p1;
    }
}
