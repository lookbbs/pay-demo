package com.ydf.pay.enums;

/**
 * 支付服务提供者
 * @author yuandongfei
 * @date 2019/5/27
 */

public enum  ProviderEnum {
    ALIPAY("alipay","支付宝支付"),
    WECHAT("wechat","微信支付")
    ;
    private String code;
    private String name;

    ProviderEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
