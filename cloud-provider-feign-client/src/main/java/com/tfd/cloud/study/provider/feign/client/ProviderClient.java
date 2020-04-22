package com.tfd.cloud.study.provider.feign.client;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @since TangFD 2020/4/22
 */
@FeignClient(name = "provider-payment", path = "/")
public interface ProviderClient {
    @PostMapping("/save")
    JsonResult<String> save(@RequestBody Payment payment);

    @GetMapping("/get/{id}")
    JsonResult<Payment> get(@PathVariable("id") String id);
}
