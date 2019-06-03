package com.ydf.pay.alipay.request;

import com.ydf.pay.entity.PayRequest;
import lombok.Data;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Data
public class AlipayOrderRequest extends PayRequest {
    private String subject;
    private String outTradeNo;
    private String totalAmount;
}
