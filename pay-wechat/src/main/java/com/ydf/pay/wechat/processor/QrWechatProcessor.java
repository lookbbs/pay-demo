package com.ydf.pay.wechat.processor;


import com.ydf.pay.wechat.WechatConfig;
import com.ydf.pay.wechat.request.QrWechatOrderRequest;
import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
@Component
public class QrWechatProcessor extends AbsWechatProcessor {

    @Autowired
    public QrWechatProcessor(WechatConfig wechatConfig) {
        super(wechatConfig);
    }

    @Override
    protected Map<String, String> buildRequestMap(WechatOrderRequest request) {
        Map<String, String> map = super.buildRequestMap(request);
        map.put("trade_type", "NATIVE");
        map.put("product_id", request.getOutTradeNo());
        return map;
    }

    @Override
    protected Document createDocument(Map<String, String> map) {
        Document document = super.createDocument(map);
        Element scene_info = document.getRootElement().addElement("product_id");
        scene_info.setText(MapUtils.getString(map, "product_id"));
        return document;
    }

    @Override
    protected WechatOrderResponse getWechatOrderResponse(String response) {
        log.info(">>> 微信二维码扫码支付返回：{}，待转换成WechatOrderResponse对象", response);
        return null;
    }

    @Override
    public boolean supports(Class<? extends WechatOrderRequest> clazz) {
        return QrWechatOrderRequest.class.isAssignableFrom(clazz);
    }
}
