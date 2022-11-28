package com.kata.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


import java.util.Date;
import java.util.TimeZone;

@TestConfiguration
@Slf4j
public class AppTestConfiguration {

    @Bean
    public TimeZone timeZone(){
        TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(defaultTimeZone);
        log.info("Spring boot application running in UTC timezone :"+new Date());
        return defaultTimeZone;
    }

}
