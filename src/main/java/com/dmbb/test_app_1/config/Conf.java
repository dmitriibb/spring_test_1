package com.dmbb.test_app_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Conf {

    public static String HELLO = "hello";
    public static String HELLO_3 = "hello3333";
    public static String HELLO_4 = "hello334444433";
    public static String HELLO_5 = "5555";
    public static String HELLO_6 = "666";

    public static String HELLO_999 = "999";
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
