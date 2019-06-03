package com.ydf.pay.alipay;

import com.ydf.pay.alipay.exception.NotFoundAlipayProcessorException;
import com.ydf.pay.alipay.request.AlipayOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class AlipayProcessorHolder {

    @Autowired
    private List<AlipayProcessor> alipayProcessors;

    public AlipayProcessor findProcessor(AlipayOrderRequest request) {
        Optional<AlipayProcessor> optional = alipayProcessors.stream().filter(alipayProcessor -> alipayProcessor.supports(request.getClass())).findFirst();
        if (!optional.isPresent()) {
            throw new NotFoundAlipayProcessorException("未找到对应{" + request.getClass().getTypeName() + "}请求类型的处理类");
        }
        return optional.get();
    }
}
