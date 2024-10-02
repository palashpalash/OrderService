package com.example.OrderService.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {


    @Bean
    public RestTemplate getTemplate()
    {
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }
}
