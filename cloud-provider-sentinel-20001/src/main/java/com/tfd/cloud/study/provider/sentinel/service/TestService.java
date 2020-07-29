package com.tfd.cloud.study.provider.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author TangFD 2020/7/29
 */
@Service
public class TestService {

    @SentinelResource(value = "test"
            , fallback = "fallback")
    public String test(String name) {
        if ("error".equals(name)) {
            throw new RuntimeException();
        }

        return "Sentinel service - " + name;
    }

    @SentinelResource(value = "haha"
            , blockHandler = "blockHandler"
            , fallback = "fallback"
            , defaultFallback = "defaultFallback")
    public String test2(String name) throws InterruptedException {
        if ("error".equals(name)) {
            throw new RuntimeException();
        }

        Thread.sleep(new Random().nextInt(3) * 1000);
        return "Sentinel service haha - " + name;
    }

    public String defaultFallback(String name, Throwable exception) {
        return "defaultFallback - " + name + "【" + exception.getMessage() + "】";
    }

    public String fallback(String name, Throwable exception) {
        return "fallback - " + name + "【" + exception.getMessage() + "】";
    }

    public String blockHandler(String name, BlockException exception) {
        return "blockHandler - " + name + "【" + exception.getMessage() + "】";
    }
}
