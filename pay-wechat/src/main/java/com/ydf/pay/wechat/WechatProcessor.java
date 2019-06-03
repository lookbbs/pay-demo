package com.ydf.pay.wechat;

import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
public interface WechatProcessor {

    /**
     * 判断是否支持当前processor
     *
     * @return
     */
    boolean supports(Class<? extends WechatOrderRequest> clazz);

    /**
     * 交易
     * @param request
     * @return
     */
    WechatOrderResponse trade(WechatOrderRequest request);
}
