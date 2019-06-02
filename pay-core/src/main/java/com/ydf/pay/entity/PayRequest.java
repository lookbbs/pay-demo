package com.ydf.pay.entity;

import com.ydf.pay.enums.ProviderEnum;
import lombok.Data;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Data
public class PayRequest {

    /**
     * 服务提供者
     */
    private ProviderEnum provider;
}
