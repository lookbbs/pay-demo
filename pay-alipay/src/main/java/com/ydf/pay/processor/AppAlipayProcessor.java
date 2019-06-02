package com.ydf.pay.processor;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.ydf.pay.AlipayConfig;
import com.ydf.pay.request.AlipayOrderRequest;
import com.ydf.pay.request.AppAlipayOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
@Component
public class AppAlipayProcessor extends AbsAlipayProcessor {

    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    private final static String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    @Override
    public boolean supports(Class<? extends AlipayOrderRequest> clazz) {
        return AppAlipayOrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected AlipayConfig getAlipayConfig() {
        return alipayConfig;
    }

    @Override
    protected AlipayResponse execute(AlipayClient client, AlipayRequest request) throws AlipayApiException {
        return client.sdkExecute(request);
    }

    @Override
    protected AlipayRequest getAlipayRequest(AlipayOrderRequest request) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(request.getSubject());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTimeoutExpress(alipayConfig.getTimeoutExpress());
        model.setTotalAmount(request.getTotalAmount());

        model.setProductCode(PRODUCT_CODE);

        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        alipayTradeAppPayRequest.setBizModel(model);
        alipayTradeAppPayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        return alipayTradeAppPayRequest;
    }
}
