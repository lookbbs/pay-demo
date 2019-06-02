package com.ydf.pay.web.controller;

import com.ydf.pay.entity.PayRequest;
import com.ydf.pay.entity.PayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付中心控制路由
 *
 * @author yuandongfei
 * @date 2019/5/27
 */
@RestController
@RequestMapping("/pay/center")
public class PayCenterController {

    /**
     * 预支付接口
     */
    @PostMapping()
    public ResponseEntity<PayResponse> payment(PayRequest request) {

        return null;
    }

    // 支付查询接口
}
