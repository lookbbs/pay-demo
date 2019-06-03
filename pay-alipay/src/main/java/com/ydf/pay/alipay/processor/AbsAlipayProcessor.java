package com.ydf.pay.alipay.processor;

import com.alipay.api.*;
import com.ydf.pay.alipay.AlipayConfig;
import com.ydf.pay.alipay.AlipayProcessor;
import com.ydf.pay.alipay.exception.AlipayTradeException;
import com.ydf.pay.alipay.exception.ValidAlipayException;
import com.ydf.pay.alipay.request.AlipayOrderRequest;
import com.ydf.pay.alipay.response.AlipayOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
public abstract class AbsAlipayProcessor implements AlipayProcessor {

    /**
     * 获取支付宝的配置信息
     *
     * @return
     */
    protected abstract AlipayConfig getAlipayConfig();

    @Override
    public AlipayOrderResponse trade(AlipayOrderRequest request) {
        // 参数校验
        validate(request);
        // 创建预支付订单
        createOrder(request);
        try {
            // 创建AlipayClient对象
            AlipayClient client = buildClient();
            // 封装请求支付信息
            AlipayRequest alipayRequest = getAlipayRequest(request);
            AlipayResponse alipayResponse = execute(client, alipayRequest);
            log.info("支付宝h5预支付 支付宝返回参数[response.getBody()]={}", alipayResponse.getBody());
            return getAlipayOrderResponse(alipayResponse);
        } catch (AlipayApiException e) {
            throw new AlipayTradeException("支付宝支付发生异常", e);
        }
    }

    /**
     * 执行调用接口
     *
     * @param client AlipayClient
     * @param request AlipayRequest
     * @exception AlipayApiException 支付宝api接口异常
     * @return AlipayResponse
     */
    protected abstract AlipayResponse execute(AlipayClient client, AlipayRequest request) throws AlipayApiException;

    /**
     * 拼装request对象
     *
     * @param request
     * @return
     */
    protected abstract AlipayRequest getAlipayRequest(AlipayOrderRequest request);

    /**
     * 处理response对象并返回
     *
     * @param response alipay响应的对象
     * @return
     */
    protected AlipayOrderResponse getAlipayOrderResponse(AlipayResponse response) {
        AlipayOrderResponse res = new AlipayOrderResponse();
        BeanUtils.copyProperties(response, res);
        return res;
    }

    private void validate(AlipayOrderRequest request) throws ValidAlipayException {
        if (StringUtils.isBlank(request.getSubject())) {
            throw new ValidAlipayException("业务订单描述不能为空");
        }
        if (StringUtils.isBlank(request.getOutTradeNo())) {
            throw new ValidAlipayException("订单号不能为空");
        }
        if (StringUtils.isBlank(request.getTotalAmount())) {
            throw new ValidAlipayException("金额为空");
        }
    }

    /**
     * 创建本地预支付单
     *
     * @param request
     * @return
     */
    private String createOrder(AlipayOrderRequest request) {
        String orderNo = UUID.randomUUID().toString();
        log.info(">>> 保存本地的预支付订单成功！预支付单号：{}, 请求（request）：{}", orderNo, request);
        return orderNo;
    }

    private AlipayClient buildClient() {
        AlipayClient client = new DefaultAlipayClient(getAlipayConfig().getServerUrl(),
                getAlipayConfig().getAppId(),
                getAlipayConfig().getPrivateKey(),
                getAlipayConfig().getFormat(),
                getAlipayConfig().getCharset(),
                getAlipayConfig().getAlipayPublicKey(),
                getAlipayConfig().getSignType());
        return client;
    }
}
