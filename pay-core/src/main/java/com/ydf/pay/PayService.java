package com.ydf.pay;

import com.ydf.pay.entity.PayRequest;
import com.ydf.pay.entity.PayResponse;

/**
 * 支付服务提供者
 * @author yuandongfei
 * @date 2019/5/27
 */
public interface PayService {

    /**
     * 支付
     * @param request 支付请求
     * @return
     */
    PayResponse payOrder(PayRequest request);

    /**
     * 支付结构查询
     * @param request
     * @return
     */
    PayResponse orderQuery(PayRequest request);
}
