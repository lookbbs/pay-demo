package com.ydf.pay;

import com.ydf.pay.request.AlipayOrderRequest;
import com.ydf.pay.response.AlipayOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class AlipayHandler {

    @Autowired
    private AlipayProcessorHolder alipayProcessorHolder;

    public AlipayOrderResponse payOrder(AlipayOrderRequest request) {
        // 根据支付类型匹配调用对应的支付接口
        return trade(request);
    }

    private AlipayOrderResponse trade(AlipayOrderRequest request) {
        // 获取对应的处理对象
        AlipayProcessor processor = alipayProcessorHolder.findProcessor(request);
        return processor.trade(request);
    }
}
