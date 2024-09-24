package com.riwi.sitekeeper.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.riwi.sitekeeper.dtos.nest.requests.TopicReq;
import com.riwi.sitekeeper.dtos.nest.requests.ValidationReq;
import com.riwi.sitekeeper.dtos.nest.responses.ApiResponse;
import com.riwi.sitekeeper.dtos.nest.responses.TopicRes;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NestServiceClient {
    private final RestTemplate restTemplate;
    private final String nestServiceUrl;

    @Autowired
    public NestServiceClient(RestTemplate restTemplate,
                             @Value("${nest.service.url}") String nestServiceUrl) {
        this.restTemplate = restTemplate;
        this.nestServiceUrl = nestServiceUrl;
    }

    public ValidationUserRes checkPermission(ValidationReq validationReq, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ValidationReq> entity = new HttpEntity<>(validationReq, headers);

        String url = nestServiceUrl + "/auth/validate";

        ResponseEntity<ApiResponse<ValidationUserRes>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData();
        } else {
            throw new RuntimeException("Failed to retrieve user data");
        }
    }

    public TopicRes getTopicById(Long id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> entity = new HttpEntity<>(headers);

        String url = nestServiceUrl + "/topic/" + id;

        ResponseEntity<ApiResponse<TopicRes>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData();
        } else {
            throw new RuntimeException("Failed to retrieve topic data");
        }

    }

}
