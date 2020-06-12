package com.tfd.cloud.study.provider.controller;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.ProviderClient;
import com.tfd.cloud.study.provider.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @since TangFD 2020-04-18
 **/
@RestController
public class PaymentController implements ProviderClient {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port:-1}")
    private String port;

    @Override
    public JsonResult<String> save(@RequestBody Payment payment) {
        return new JsonResult<>(true, port, paymentService.save(payment));
    }

    @Override
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        try {
            Thread.sleep(new Random().nextInt(1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JsonResult<>(true, port, paymentService.get(id));
    }
}
