package com.ydf.pay;

import com.ydf.pay.request.AlipayOrderRequest;
import com.ydf.pay.response.AlipayOrderResponse;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
public interface AlipayProcessor {

    /**
     * 判断是否支持当前processor
     *
     * @return
     */
    boolean supports(Class<? extends AlipayOrderRequest> clazz);

    /**
     * 交易
     * @param request
     * @return
     */
    AlipayOrderResponse trade(AlipayOrderRequest request);
}
