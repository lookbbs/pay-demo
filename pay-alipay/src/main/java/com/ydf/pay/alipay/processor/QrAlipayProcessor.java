package com.ydf.pay.alipay.processor;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.ydf.pay.alipay.AlipayConfig;
import com.ydf.pay.alipay.request.AlipayOrderRequest;
import com.ydf.pay.alipay.request.QrAlipayOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class QrAlipayProcessor extends AbsAlipayProcessor {

    @Autowired
    private AlipayConfig alipayConfig;
    /**
     * 销售产品码。
     * 如果签约的是当面付快捷版，则传OFFLINE_PAYMENT;
     * 其它支付宝当面付产品传FACE_TO_FACE_PAYMENT；
     * 不传默认使用FACE_TO_FACE_PAYMENT；
     */
    private final static String PRODUCT_CODE = "FACE_TO_FACE_PAYMENT";

    @Override
    public boolean supports(Class<? extends AlipayOrderRequest> clazz) {
        return QrAlipayOrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected AlipayConfig getAlipayConfig() {
        return alipayConfig;
    }

    @Override
    protected AlipayResponse execute(AlipayClient client, AlipayRequest request) throws AlipayApiException {
        return client.execute(request);
    }

    @Override
    protected AlipayRequest getAlipayRequest(AlipayOrderRequest request) {
        AlipayTradePrecreateRequest tradePrecreateRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(request.getOutTradeNo());
        model.setSubject(request.getSubject());
        model.setTimeoutExpress(alipayConfig.getTimeoutExpress());
        model.setTotalAmount(request.getTotalAmount());
        model.setDisablePayChannels(alipayConfig.getDisablePayChannels());
        tradePrecreateRequest.setBizModel(model);
        tradePrecreateRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        tradePrecreateRequest.setProdCode(PRODUCT_CODE);
        return tradePrecreateRequest;
    }
}
