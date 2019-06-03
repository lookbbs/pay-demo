package com.ydf.pay.wechat.processor;

import com.ydf.pay.util.HttpClientUtil;
import com.ydf.pay.wechat.WechatConfig;
import com.ydf.pay.wechat.WechatProcessor;
import com.ydf.pay.wechat.exception.ValidWechatException;
import com.ydf.pay.wechat.exception.WechatTradeException;
import com.ydf.pay.wechat.request.WechatOrderRequest;
import com.ydf.pay.wechat.response.WechatOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Slf4j
public abstract class AbsWechatProcessor implements WechatProcessor {

    private final WechatConfig wechatConfig;

    private final static String KEY_SIGN = "sign";

    protected AbsWechatProcessor(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    @Override
    public WechatOrderResponse trade(WechatOrderRequest request) {
        // 参数校验
        validate(request);
        // 创建预支付订单
        createOrder(request);
        try {
            String response = execute(request);
            return getWechatOrderResponse(response);
        } catch (Exception e) {
            throw new WechatTradeException("微信支付发生异常", e);
        }
    }

    /**
     * 执行调用接口
     *
     * @param request WechatOrderRequest
     * @return WechatOrderResponse
     */
    private String execute(WechatOrderRequest request) throws Exception {
        String serverUrl = wechatConfig.getServerUrl();
        log.info(">>> 请求微信支付的URL：{}", serverUrl);
        String reqStr = getWechatRequest(request);
        log.info(">>> 请求微信支付请求的参数：{}", reqStr);
        String res = HttpClientUtil.httpPost(serverUrl, reqStr);
        log.info(">>> 请求微信支付响应的结果：{}", res);
        return res;
    }

    /**
     * 拼装request对象
     *
     * @param request
     * @return
     */
    private String getWechatRequest(WechatOrderRequest request) throws IOException {
        Map<String, String> map = buildRequestMap(request);
        // 去除空value的key,移除sign key
        removeKeyByEmptyValue(map);
        // 排序keys
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        //拼接字符串key=value&key2=value...
        StringBuilder builder = new StringBuilder();
        keys.forEach(k -> builder.append(k).append("=").append(map.get(k)).append("&"));
        builder.append("key=").append(wechatConfig.getKey());
        String sign = DigestUtils.md5DigestAsHex(builder.toString().getBytes("UTF-8"));
        map.put("sign", sign);
        return createDocument(map).getRootElement().asXML();
    }

    /**
     * 拼装请求的参数
     *
     * @param request
     * @return
     */
    protected Map<String, String> buildRequestMap(WechatOrderRequest request) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("appid", request.getAppId());
        map.put("mch_id", request.getMchId());
        map.put("nonce_str", request.getNonceStr());
        map.put("body", request.getBody());
        map.put("out_trade_no", request.getOutTradeNo());
        map.put("total_fee", request.getTotalFee());
        map.put("spbill_create_ip", request.getSpbillCreateIp());
        map.put("time_start", request.getTimeStart());
        map.put("time_expire", request.getTimeExpire());
        map.put("notify_url", request.getNotifyUrl());
        return map;
    }

    /**
     * 拼装请求的xml对象
     * 子类可以覆盖该方法
     * @param map
     * @return
     */
    protected Document createDocument(Map<String, String> map) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        document.setRootElement(root);

        Element appid = root.addElement("appid");
        appid.setText(MapUtils.getString(map, "appid"));

        Element mchId = root.addElement("mch_id");
        mchId.setText(MapUtils.getString(map, "mch_id"));
        if (StringUtils.isNotBlank(map.get("device_info"))) {
            Element deviceInfo = root.addElement("device_info");
            deviceInfo.setText(MapUtils.getString(map, "device_info"));
        }

        Element nonceStr = root.addElement("nonce_str");
        nonceStr.setText(MapUtils.getString(map, "nonce_str"));

        Element sign = root.addElement("sign");
        sign.setText(MapUtils.getString(map, "sign"));

        Element body = root.addElement("body");
        body.setText(MapUtils.getString(map, "body"));
        if (StringUtils.isNotBlank(MapUtils.getString(map, "detail"))) {
            Element detail = root.addElement("detail");
            detail.setText(MapUtils.getString(map, "detail"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "attach"))) {
            Element attach = root.addElement("attach");
            attach.setText(MapUtils.getString(map, "attach"));
        }

        Element outTradeNo = root.addElement("out_trade_no");
        outTradeNo.setText(MapUtils.getString(map, "out_trade_no"));
        if (StringUtils.isNotBlank(MapUtils.getString(map, "fee_type"))) {
            Element feeType = root.addElement("fee_type");
            feeType.setText(MapUtils.getString(map, "fee_type"));
        }
        Element totalFee = root.addElement("total_fee");
        totalFee.setText(MapUtils.getString(map, "total_fee"));

        Element spbillCreateIp = root.addElement("spbill_create_ip");
        spbillCreateIp.setText(MapUtils.getString(map, "spbill_create_ip"));

        Element timeStart = root.addElement("time_start");
        timeStart.setText(MapUtils.getString(map, "time_start"));

        Element timeExpire = root.addElement("time_expire");
        timeExpire.setText(MapUtils.getString(map, "time_expire"));

        if (StringUtils.isNotBlank(MapUtils.getString(map, "goods_tag"))) {
            Element goodsTag = root.addElement("goods_tag");
            goodsTag.setText(MapUtils.getString(map, "goods_tag"));
        }
        Element notifyUrl = root.addElement("notify_url");
        notifyUrl.setText(MapUtils.getString(map, "notify_url"));

        Element tradeType = root.addElement("trade_type");
        tradeType.setText(MapUtils.getString(map, "trade_type"));
        return document;
    }


    /**
     * 处理response对象并返回
     *
     * @param response wechat响应的对象
     * @return
     */
    protected abstract WechatOrderResponse getWechatOrderResponse(String response);

    private void validate(WechatOrderRequest request) throws ValidWechatException {
        if (StringUtils.isBlank(request.getBody())) {
            throw new ValidWechatException("业务订单描述不能为空");
        }
        if (StringUtils.isBlank(request.getOutTradeNo())) {
            throw new ValidWechatException("订单号不能为空");
        }
        if (StringUtils.isBlank(request.getTotalFee())) {
            throw new ValidWechatException("金额为空");
        }
    }

    /**
     * 创建本地预支付单
     *
     * @param request
     * @return
     */
    private String createOrder(WechatOrderRequest request) {
        String orderNo = UUID.randomUUID().toString();
        log.info(">>> 保存本地的预支付订单成功！预支付单号：{}, 请求（request）：{}", orderNo, request);
        return orderNo;
    }

    /**
     * 删除value为空的key，和key名为sign的key
     *
     * @param map
     */
    private void removeKeyByEmptyValue(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String v = entry.getValue();
            if (StringUtils.isBlank(v) || KEY_SIGN.equalsIgnoreCase(key)) {
                map.remove(key);
            }
        }
    }
}
