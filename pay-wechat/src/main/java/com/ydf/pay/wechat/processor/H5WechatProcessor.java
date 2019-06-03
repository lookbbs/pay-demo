package com.ydf.pay.wechat.processor;

import com.ydf.pay.wechat.WechatConfig;
import com.ydf.pay.wechat.request.H5WechatOrderRequest;
import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 支付宝手机网站支付接口
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
@Component
public class H5WechatProcessor extends AbsWechatProcessor {

    public H5WechatProcessor(WechatConfig wechatConfig) {
        super(wechatConfig);
    }

    @Override
    public boolean supports(Class<? extends WechatOrderRequest> clazz) {
        return H5WechatOrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected Map<String, String> buildRequestMap(WechatOrderRequest request) {
        Map<String, String> map = super.buildRequestMap(request);
        map.put("trade_type", "MWEB");
        map.put("scene_info", request.getSceneInfo());
        return map;
    }

    @Override
    protected Document createDocument(Map<String, String> map) {
        Document document = super.createDocument(map);
        Element sceneInfo = document.getRootElement().addElement("scene_info");
        sceneInfo.setText(MapUtils.getString(map, "scene_info"));
        return document;
    }

    @Override
    protected WechatOrderResponse getWechatOrderResponse(String response) {
        log.info(">>> 微信H5支付返回：{}，待转换成WechatOrderResponse对象", response);
        return null;
    }
}
