package com.tfd.cloud.study.provider.sentinel.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TangFD 2020-07-28
 **/
@RestController
public class SentinelController {

    @RequestMapping("/test/{name}")
    public String test(@PathVariable("name") String name) {
        return "Sentinel - " + name;
    }
}
