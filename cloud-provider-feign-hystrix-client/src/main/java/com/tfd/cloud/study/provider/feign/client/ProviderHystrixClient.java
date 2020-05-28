package com.tfd.cloud.study.provider.feign.client;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.fallback.ProviderHystrixFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @since TangFD 2020/4/22
 */
@FeignClient(name = "provider-payment-hystrix", path = "/", fallback = ProviderHystrixFallBack.class)
public interface ProviderHystrixClient {
    @PostMapping("/save")
    JsonResult<String> save(@RequestBody Payment payment);

    @GetMapping("/get/{id}")
    JsonResult<Payment> get(@PathVariable("id") String id);

    @GetMapping("/getE/{id}")
    JsonResult<Payment> getE(@PathVariable("id") String id);

    @GetMapping("/getIgnE/{id}")
    JsonResult<Payment> getIgnE(@PathVariable("id") String id);
}
