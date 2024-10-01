package com.dmbb.test_app_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Conf {

    public static String HELLO = "hello";
    public static String HELLO_2 = "hello222";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
