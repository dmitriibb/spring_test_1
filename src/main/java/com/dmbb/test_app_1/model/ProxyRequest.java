package com.dmbb.test_app_1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Getter
@Setter
public class ProxyRequest {

    private String url;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private int requestNumber;
    private int delayBetweenRequestsMs;

    private String requestBodyFileName;
    private Map<String, Object> requestBody;

}
