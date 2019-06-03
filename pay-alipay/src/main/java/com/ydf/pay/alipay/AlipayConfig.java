package com.ydf.pay.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    private String serverUrl;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String notifyUrl;
    /**
     * h5支付单独使用的，支付完成后跳转的地址
     */
    private String h5ReturnUrl;
    /**
     * m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
     */
    private String timeoutExpress;
    /**
     * 禁用渠道，用户不可用指定渠道支付
     * 当有多个渠道时用“,”分隔
     * 渠道列表：https://docs.open.alipay.com/common/wifww7
     * 注，与enable_pay_channels互斥, 默认：禁止信用付款
     * credit_group: 信用支付类型（包含 信用卡卡通，信用卡快捷,花呗，花呗分期）
     */
    private String disablePayChannels = "credit_group";
    private String format = "json";
    private String signType = "RSA2";
    private String charset = "UTF-8";
}
