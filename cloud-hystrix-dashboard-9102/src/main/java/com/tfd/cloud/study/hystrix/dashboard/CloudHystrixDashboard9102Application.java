package com.tfd.cloud.study.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class CloudHystrixDashboard9102Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudHystrixDashboard9102Application.class, args);
    }

}
