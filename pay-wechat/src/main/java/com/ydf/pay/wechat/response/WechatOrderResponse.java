package com.ydf.pay.wechat.response;

import com.ydf.pay.entity.PayResponse;
import lombok.Data;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Data
public class WechatOrderResponse extends PayResponse {
    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;
    /**
     * 收款微信账号对应的微信唯一用户号。
     */
    private String sellerId;
    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
    private String totalAmount;
    /**
     * 该交易在微信系统中的交易流水号。
     */
    private String tradeNo;
}
