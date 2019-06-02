package com.ydf.pay;

import com.ydf.pay.entity.PayRequest;
import com.ydf.pay.entity.PayResponse;
import org.springframework.stereotype.Component;

/**
 * @author yuandongfei
 * @date 2019/5/27
 */
@Component
public class PayServiceImpl implements PayService {


    @Override
    public PayResponse payOrder(PayRequest request) {
        return null;
    }

    @Override
    public PayResponse orderQuery(PayRequest request) {
        return null;
    }
}
