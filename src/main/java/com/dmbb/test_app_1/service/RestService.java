package com.dmbb.test_app_1.service;

import com.dmbb.test_app_1.model.ProxyRequest;
import com.dmbb.test_app_1.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class RestService {

    @Autowired
    public RestTemplate restTemplate;

    public ExecutorService executorCached = Executors.newCachedThreadPool();

    public ExecutorService executor = Executors.newFixedThreadPool(10);

    public List<String> sendHttpRequest(ProxyRequest proxyRequest) throws InterruptedException, ExecutionException {

        if (!StringUtils.isEmpty(proxyRequest.getRequestBodyFileName())){
            Map<String, Object> body = MyUtils.readJson(proxyRequest.getRequestBodyFileName(), Map.class);
            proxyRequest.setRequestBody(body);
        }

        List<RequestCallable> callableList = creteCallableList(proxyRequest);
        List<Future<String>> futureList = new ArrayList<>();

        for (RequestCallable callable : callableList) {
            Thread.sleep(proxyRequest.getDelayBetweenRequestsMs());
            futureList.add(executor.submit(callable));
        }

        List<String> responses = new ArrayList<>();
        for (Future<String> future : futureList) {
            responses.add(future.get());
        }

        return responses;
    }

    public List<RequestCallable> creteCallableList(ProxyRequest proxyRequest) {
        List<RequestCallable> list = new ArrayList<>();
        for (int i = 0; i < proxyRequest.getRequestNumber(); i++) {
            RequestCallable callable = new RequestCallable(restTemplate, proxyRequest, i);
            list.add(callable);
        }
        return list;
    }

    public static class RequestCallable implements Callable<String> {

        private RestTemplate restTemplate;
        private ProxyRequest proxyRequest;
        private int requestId;

        public RequestCallable(RestTemplate restTemplate, ProxyRequest proxyRequest, int requestId) {
            this.restTemplate = restTemplate;
            this.proxyRequest = proxyRequest;
            this.requestId = requestId;
        }

        @Override
        public String call() throws Exception {
            HttpHeaders headers = createHeaders();
            HttpEntity entity = new HttpEntity(proxyRequest.getRequestBody(), headers);
            long startTime = System.currentTimeMillis();

            ResponseEntity<String> response = restTemplate.exchange(proxyRequest.getUrl(), proxyRequest.getHttpMethod(), entity, String.class);

            long timeTaken = System.currentTimeMillis() - startTime;
            String result = "ok - " + requestId + ", time taken: " + timeTaken + " (ms)";
            log.info(result);
            return result;
        }

        public HttpHeaders createHeaders() {
            HttpHeaders headers = new HttpHeaders();
            if (proxyRequest.getHeaders() != null) {
                proxyRequest.getHeaders().entrySet().forEach(entry -> headers.add(entry.getKey(), entry.getValue()));
            }
            return headers;
        }

    }


}
