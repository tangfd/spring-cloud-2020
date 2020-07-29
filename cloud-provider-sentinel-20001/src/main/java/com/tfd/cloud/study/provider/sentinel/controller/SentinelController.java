package com.tfd.cloud.study.provider.sentinel.controller;

import com.tfd.cloud.study.provider.sentinel.service.TestService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author TangFD 2020-07-28
 **/
@RestController
public class SentinelController {

    @Resource
    private TestService testService;

    @RequestMapping("/test/{name}")
    public String test(@PathVariable("name") String name) throws InterruptedException {
        return Thread.currentThread().getName() + " : " + testService.test(name);
    }

    @RequestMapping("/haha/{name}")
    public String haha(@PathVariable("name") String name) throws InterruptedException {
        return Thread.currentThread().getName() + " : " + testService.test2(name);
    }
}
