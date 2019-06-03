package com.ydf.pay.wechat.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ydf.pay.entity.PayRequest;
import lombok.Data;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Data
public class WechatOrderRequest extends PayRequest {
    /**
     * 公众账号ID
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    @JsonProperty("mch_id")
    private String mchId;
    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，
     * PC网页或公众号内支付可以传"WEB"
     */
    @JsonProperty("device_info")
    private String deviceInfo = "WEB";

    /**
     * 随机字符串，长度要求在32位以内。
     */
    @JsonProperty("nonce_str")
    private String nonceStr;

    /**
     * 通过签名算法计算得出的签名值
     */
    private String sign;

    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     */
    @JsonProperty("sign_type")
    private String signType = "MD5";

    /**
     * 商品简单描述
     */
    private String body;

    /**
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，
     */
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回
     */
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type = "CNY";

    /**
     * 订单总金额，单位为分
     */
    @JsonProperty("total_fee")
    private String totalFee;

    /**
     * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     */
    @JsonProperty("time_start")
    private String timeStart;


    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
     * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     */
    @JsonProperty("time_expire")
    private String timeExpire;

    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
     */
    @JsonProperty("goods_tag")
    private String goodsTag;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * JSAPI 公众号支付,
     * NATIVE 扫码支付,
     * APP APP支付
     */
    @JsonProperty("trade_type")
    private String tradeType;

    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    @JsonProperty("limit_pay")
    private String limitPay = "no_credit";

    /**
     * trade_type=JSAPI时（即公众号支付），此参数必传，
     * 此参数为微信用户在商户对应appid下的唯一标识。
     * openid如何获取，可参考【获取openid】。
     * 企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     */
    private String openid;

    /**
     * 该字段用于上报场景信息，目前支持上报实际门店信息。
     * 该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
     */
    @JsonProperty("scene_info")
    private String sceneInfo;
}
