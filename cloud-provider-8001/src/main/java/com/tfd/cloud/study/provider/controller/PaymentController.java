package com.tfd.cloud.study.provider.controller;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @since TangFD 2020-04-18
 **/
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping("/save")
    public JsonResult<String> save(@RequestBody Payment payment) {
        return new JsonResult<>(paymentService.save(payment));
    }

    @GetMapping("/get/{id}")
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        return new JsonResult<>(true, null, paymentService.get(id));
    }
}
