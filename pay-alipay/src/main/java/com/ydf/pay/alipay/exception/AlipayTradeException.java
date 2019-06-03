package com.ydf.pay.alipay.exception;

/**
 * 支付宝交易异常
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
public class AlipayTradeException extends AlipayException {
    public AlipayTradeException(String message) {
        super(message);
    }

    public AlipayTradeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
