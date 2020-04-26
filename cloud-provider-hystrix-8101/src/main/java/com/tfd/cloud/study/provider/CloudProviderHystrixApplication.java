package com.tfd.cloud.study.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan("com.tfd.cloud.study")
public class CloudProviderHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudProviderHystrixApplication.class, args);
    }

}
