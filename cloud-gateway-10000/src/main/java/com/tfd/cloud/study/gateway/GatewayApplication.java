package com.tfd.cloud.study.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.ZonedDateTime;

/**
 * http://localhost:10100/actuator/gateway/routes gateway 提供路由信息
 *
 * @since TangFD 2020-06-02
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println(ZonedDateTime.now());
    }
}
