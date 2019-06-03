package com.ydf.pay.alipay.processor;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.ydf.pay.alipay.AlipayConfig;
import com.ydf.pay.alipay.request.AlipayOrderRequest;
import com.ydf.pay.alipay.request.H5AlipayOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付宝手机网站支付接口
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
@Component
public class H5AlipayProcessor extends AbsAlipayProcessor {

    @Autowired
    private AlipayConfig alipayConfig;

    private final static String PRODUCT_CODE = "QUICK_WAP_WAY";

    @Override
    public boolean supports(Class<? extends AlipayOrderRequest> clazz) {
        return H5AlipayOrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected AlipayConfig getAlipayConfig() {
        return alipayConfig;
    }

    @Override
    protected AlipayResponse execute(AlipayClient client, AlipayRequest request) throws AlipayApiException {
        log.info("支付宝H5支付重定向地址url:{}", request.getReturnUrl());
        return client.pageExecute(request);
    }

    @Override
    protected AlipayRequest getAlipayRequest(AlipayOrderRequest request) {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(request.getOutTradeNo());
        model.setSubject(request.getSubject());
        model.setTimeoutExpress(alipayConfig.getTimeoutExpress());
        model.setTotalAmount(request.getTotalAmount());

        model.setProductCode(PRODUCT_CODE);
        //禁止信用付款
        model.setDisablePayChannels(alipayConfig.getDisablePayChannels());

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setBizModel(model);
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        // 设置同步地址
        alipayRequest.setReturnUrl(String.format("%s?key=value", alipayConfig.getH5ReturnUrl()));
        return alipayRequest;
    }
}
