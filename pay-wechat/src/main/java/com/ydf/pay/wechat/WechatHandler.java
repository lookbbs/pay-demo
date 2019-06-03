package com.ydf.pay.wechat;

import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class WechatHandler {

    @Autowired
    private WechatProcessorHolder wechatProcessorHolder;

    public WechatOrderResponse payOrder(WechatOrderRequest request) {
        // 根据支付类型匹配调用对应的支付接口
        return trade(request);
    }

    private WechatOrderResponse trade(WechatOrderRequest request) {
        // 获取对应的处理对象
        WechatProcessor processor = wechatProcessorHolder.findProcessor(request);
        return processor.trade(request);
    }
}
