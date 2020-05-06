package com.tfd.cloud.study.provider.feign.client.fallback;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.ProviderHystrixClient;

/**
 * @since TangFD 2020-05-06
 **/
public class ProviderHystrixFallBack implements ProviderHystrixClient {
    @Override
    public JsonResult<String> save(Payment payment) {
        return new JsonResult<>(false, "这是Feign client 配置的 fallback");
    }

    @Override
    public JsonResult<Payment> get(String id) {
        return new JsonResult<>(false, "这是Feign client 配置的 fallback");
    }

    @Override
    public JsonResult<Payment> getE(String id) {
        return new JsonResult<>(false, "这是Feign client 配置的 fallback");
    }
}
