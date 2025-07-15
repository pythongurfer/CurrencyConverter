package com.example.currencyconverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean // this method produces a bean to be managed by the Spring container. now we can @autowired a RestTemplate anywhere in our app
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
