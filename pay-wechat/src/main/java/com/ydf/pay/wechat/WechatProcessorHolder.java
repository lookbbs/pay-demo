package com.ydf.pay.wechat;

import com.ydf.pay.wechat.exception.NotFoundWechatProcessorException;
import com.ydf.pay.wechat.request.WechatOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class WechatProcessorHolder {

    @Autowired
    private List<WechatProcessor> wechatProcessors;

    public WechatProcessor findProcessor(WechatOrderRequest request) {
        Optional<WechatProcessor> optional = wechatProcessors.stream().filter(wechatProcessor -> wechatProcessor.supports(request.getClass())).findFirst();
        if (!optional.isPresent()) {
            throw new NotFoundWechatProcessorException("未找到对应{" + request.getClass().getTypeName() + "}请求类型的处理类");
        }
        return optional.get();
    }
}
