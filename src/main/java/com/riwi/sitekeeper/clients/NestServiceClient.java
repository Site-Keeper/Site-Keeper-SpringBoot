package com.riwi.sitekeeper.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.riwi.sitekeeper.dtos.nest.responses.ApiResponse;
import com.riwi.sitekeeper.dtos.nest.responses.ValidationRes;
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

    public ValidationUserRes checkPermission(String targetEntity, String permissions, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = nestServiceUrl + "/auth/validate";

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class
        );

        if (response.getBody() != null && response.getBody().has("data")) {
            JsonNode data = response.getBody().get("data");
            Long id = data.get("id").asLong();
            String name = data.get("role").get("name").asText();
            return new ValidationUserRes(id, name);
        } else {
            throw new RuntimeException("Failed to retrieve user data");
        }
    }
}
