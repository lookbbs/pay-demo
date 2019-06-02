package com.ydf.pay.exception;

/**
 * alipay 处理异常
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
public class AlipayException extends PayException {
    public AlipayException(String message) {
        super(message);
    }

    public AlipayException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
