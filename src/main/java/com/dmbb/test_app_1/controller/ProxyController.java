package com.dmbb.test_app_1.controller;

import com.dmbb.test_app_1.model.ProxyRequest;
import com.dmbb.test_app_1.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @Autowired
    public RestService restService;

    @PostMapping
    public List<String> sendProxyRequest(@RequestBody ProxyRequest proxyRequest) throws ExecutionException, InterruptedException {
        return restService.sendHttpRequest(proxyRequest);
    }

}
