package org.jeecg.modules.demo.wechat.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.shop.entity.DomesticOrder;
import org.jeecg.modules.demo.shop.service.IDomesticOrderService;
import org.jeecg.modules.demo.wechat.pay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.ControllerAdviceBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "微信支付回调")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private IDomesticOrderService domesticOrderService;
    @Autowired
    private MyWxPayConfig config;

    @PostMapping("/wxNotify/{id}")
    @ResponseBody
    public String wxNotify(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        String returnMsg = "FAIL";
        String returnCode = "未知的处理结果";

        String resultXml = "";
        try {
            String xml = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WXPay wxPay = new WXPay(config);
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
            log.info("------统一支付接口回调数据------" + notifyMap.toString());
            if (WXPayUtil.isSignatureValid(notifyMap, this.config.pkey(), WXPayConstants.SignType.HMACSHA256)) {
                // 签名正确，做业务处理。
                if ("SUCCESS".equals(notifyMap.get("return_code")) && "SUCCESS".equals(notifyMap.get("result_code"))) {
                    DomesticOrder domesticOrder = domesticOrderService.getById(id);
                    if(domesticOrder == null){
                        returnMsg = "订单ID错误";
                        returnCode = "FAIL";
                    }else if (domesticOrder.getOrderState() == 2) {
                        // 待支付改成已支付
                        DomesticOrder nDomesticOrder = new DomesticOrder();
                        nDomesticOrder.setId(domesticOrder.getId());
                        nDomesticOrder.setOrderState(1);
                        domesticOrderService.updateById(nDomesticOrder);
                        returnMsg = "订单处理成功";
                        returnCode = "SUCCESS";
                    }else{
                        returnMsg = "订单状态不对";
                        returnCode = "SUCCESS";
                    }
                } else {
                    returnMsg = "交易失败";
                    returnCode = "FAIL";
                }
            } else {
                // 响应签名错误
                returnMsg = "签名失败";
                returnCode = "FAIL";
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMsg = "回调处理异常";
            returnCode = "FAIL";
        } finally {
            resultXml = "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>";
        }
        log.info("------统一支付接口回调响应------" + resultXml);
        return resultXml;
    }
}
