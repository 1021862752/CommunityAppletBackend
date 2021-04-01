package org.jeecg.modules.demo.wechat.pay;

public class WXPayDomain implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
    }
}
