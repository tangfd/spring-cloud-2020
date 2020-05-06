package com.tfd.cloud.study.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.ProviderHystrixClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 需要配置default fallback 才可对服务接口进行兜底
 *
 * @since TangFD 2020-04-18
 **/
@RestController
@RequestMapping("/c2")
public class ConsumerController2 {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ProviderHystrixClient providerHystrixClient;

    @Resource
    private DiscoveryClient discoveryClient;
    // 对于zookeeper注册中心，服务名对大小写是敏感的
    private static final String PROVIDER_URL = "http://provider-payment-hystrix/";

    @PostMapping("/save")
    public JsonResult<String> save(@RequestBody Payment payment) {
        return restTemplate.postForObject(PROVIDER_URL + "save", payment, JsonResult.class);
    }

    @GetMapping("/get/{id}")
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        return restTemplate.getForObject(PROVIDER_URL + "get/" + id, JsonResult.class);
    }

    @GetMapping("/getE/{id}")
    public JsonResult<Payment> getE(@PathVariable("id") String id) {
        return restTemplate.getForObject(PROVIDER_URL + "getE/" + id, JsonResult.class);
    }

    @PostMapping("/saveF")
    public JsonResult<String> saveF(@RequestBody Payment payment) {
        return providerHystrixClient.save(payment);
    }

    /**
     * 自定义的fallback
     */
    @GetMapping("/getF/{id}")
    @HystrixCommand(fallbackMethod = "getFall2", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public JsonResult<Payment> getF(@PathVariable("id") String id) {
        return providerHystrixClient.get(id);
    }

    /**
     * 默认的fallback
     */
    @HystrixCommand
    @GetMapping("/getFD/{id}")
    public JsonResult<Payment> getFD(@PathVariable("id") String id) {
        return providerHystrixClient.get(id);
    }

    /**
     * 服务端的fallback
     */
    @GetMapping("/getFE/{id}")
    public JsonResult<Payment> getFE(@PathVariable("id") String id) {
        return providerHystrixClient.getE(id);
    }

    public JsonResult<Payment> getFall2(String id) {
        return new JsonResult<>(false, "c2: 这是客户端的fallback方法");
    }

}
