package com.tfd.cloud.study.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @since TangFD 2020-04-18
 **/
@EnableFeignClients("com.tfd.cloud.study.provider.feign.client")
@SpringBootApplication
public class ConsumerHystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerHystrixApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
