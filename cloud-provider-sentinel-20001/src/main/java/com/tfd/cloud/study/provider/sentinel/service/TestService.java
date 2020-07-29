package com.tfd.cloud.study.provider.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author TangFD 2020/7/29
 */
@Service
public class TestService {

    @SentinelResource("test")
    public String test(String name) throws InterruptedException {
        Thread.sleep(new Random().nextInt(3) * 1000);
        return "Sentinel service - " + name;
    }

    @SentinelResource("haha")
    public String test2(String name) throws InterruptedException {
        Thread.sleep(new Random().nextInt(3) * 1000);
        return "Sentinel service haha - " + name;
    }
}
