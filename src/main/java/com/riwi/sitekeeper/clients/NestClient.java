package com.riwi.sitekeeper.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class NestClient {

    private final RestTemplate restTemplate;
    private final String nestServiceUrl;

    @Autowired
    NestClient(RestTemplate restTemplate, @Value("${nest.service.url}") String nestServiceUrl) {
        this.restTemplate = restTemplate;
        this.nestServiceUrl = nestServiceUrl;
    }



}
