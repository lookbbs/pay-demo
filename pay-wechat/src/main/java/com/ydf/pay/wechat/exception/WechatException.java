package com.ydf.pay.wechat.exception;

import com.ydf.pay.alipay.exception.PayException;

/**
 * wechat 处理异常
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
public class WechatException extends PayException {
    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
