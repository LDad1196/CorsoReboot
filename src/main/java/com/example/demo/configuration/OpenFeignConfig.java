package com.example.demo.configuration;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OpenFeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC; // NONE, BASIC, HEADERS, FULL
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                5000, TimeUnit.MILLISECONDS, // connectTimeout
                10000, TimeUnit.MILLISECONDS, // readTimeout
                true // followRedirects
        );
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                100, // period iniziale
                1000, // periodo massimo
                3 // max attempts
        );
    }
}

