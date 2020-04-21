package com.tfd.cloud.study.consumer.controller;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @since TangFD 2020-04-18
 **/
@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;
    // 对于zookeeper注册中心，服务名对大小写是敏感的
    private static final String PROVIDER_URL = "http://provider-payment/";

    @PostMapping("/save")
    public JsonResult<String> save(@RequestBody Payment payment) {
        return restTemplate.postForObject(PROVIDER_URL + "save", payment, JsonResult.class);
    }

    @GetMapping("/get/{id}")
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        return restTemplate.getForObject(PROVIDER_URL + "get/" + id, JsonResult.class);
    }

    @GetMapping("/server")
    public List<ServiceInstance> getServer() {
        List<ServiceInstance> instances = new ArrayList<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            instances.addAll(discoveryClient.getInstances(service));
        }

        return instances;
    }
}
