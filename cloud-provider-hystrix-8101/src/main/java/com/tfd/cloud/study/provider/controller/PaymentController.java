package com.tfd.cloud.study.provider.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.ProviderHystrixClient;
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
public class PaymentController implements ProviderHystrixClient {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port:-1}")
    private String port;

    @Override
    public JsonResult<String> save(@RequestBody Payment payment) {
        return new JsonResult<>(true, port, paymentService.save(payment));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFall", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        try {
            Thread.sleep(new Random().nextInt(7) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JsonResult<>(true, port, paymentService.get(id));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFall")
    public JsonResult<Payment> getE(@PathVariable("id") String id) {
        int a = 1 / 0;
        return new JsonResult<>();
    }

    public JsonResult<Payment> getFall(String id) {
        return new JsonResult<>(false, "这是服务端的fallback方法");
    }
}
