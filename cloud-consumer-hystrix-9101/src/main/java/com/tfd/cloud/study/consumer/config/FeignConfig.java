package com.tfd.cloud.study.consumer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since TangFD 2020-04-23
 **/
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
