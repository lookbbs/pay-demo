package com.ydf.pay.exception;

/**
 * 支付异常父类
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
public class PayException extends RuntimeException {
    public PayException(String message) {
        super(message);
    }

    public PayException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
