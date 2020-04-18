package com.tfd.cloud.study.consumer.controller;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @since TangFD 2020-04-18
 **/
@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://localhost:8001/";

    @PostMapping("/save")
    public JsonResult<String> save(@RequestBody Payment payment) {
        return restTemplate.postForObject(PROVIDER_URL + "save", payment, JsonResult.class);
    }

    @GetMapping("/get/{id}")
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        return restTemplate.getForObject(PROVIDER_URL + "get/" + id, JsonResult.class);
    }

}
