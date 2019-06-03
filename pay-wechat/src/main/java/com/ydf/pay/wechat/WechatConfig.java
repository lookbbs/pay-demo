package com.ydf.pay.wechat;

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
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
    private String appId;
    private String mchId;
    private String key;
    private String notifyUrl;
    private String serverUrl;
}
