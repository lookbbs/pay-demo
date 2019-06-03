package com.ydf.pay.wechat.processor;

import com.ydf.pay.wechat.WechatConfig;
import com.ydf.pay.wechat.request.AppWechatOrderRequest;
import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
@Component
public class AppWechatProcessor extends AbsWechatProcessor {


    @Autowired
    public AppWechatProcessor(WechatConfig wechatConfig) {
        super(wechatConfig);
    }

    @Override
    public boolean supports(Class<? extends WechatOrderRequest> clazz) {
        return AppWechatOrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected Map<String, String> buildRequestMap(WechatOrderRequest request) {
        Map<String, String> map = super.buildRequestMap(request);
        map.put("trade_type", "APP");
        return map;
    }

    @Override
    protected WechatOrderResponse getWechatOrderResponse(String response) {
        log.info(">>> 微信APP支付返回：{}，待转换成WechatOrderResponse对象", response);
        return null;
    }

}
