package com.ydf.pay.wechat.exception;

/**
 * 微信支付交易异常
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
public class WechatTradeException extends WechatException {
    public WechatTradeException(String message) {
        super(message);
    }

    public WechatTradeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
