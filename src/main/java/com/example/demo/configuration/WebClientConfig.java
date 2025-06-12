package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean(name = "utentiWebClient")
    public WebClient utentiWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9090/api")
                .defaultHeaders(headers -> headers.setBasicAuth("admin", "admin123"))
                .build();
    }

    @Bean(name = "authWebClient")
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9090/api")
                .build();
    }
}