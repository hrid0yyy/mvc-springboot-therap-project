package com.therap.javafest.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.therap.javafest.security.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentApiClient {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final SessionService sessionService;

    @Value("${app.fastapi.url}")
    private String agentApiUrl;

    public Map<String, Object> chat(String query, String threadId) {
        try {
            String url = agentApiUrl + "/chat";
            String email = sessionService.getEmail();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("query", query);
            requestBody.put("email", email);
            requestBody.put("thread_id", threadId);
            
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to send chat message: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error sending chat message: " + e.getMessage(), e);
        }
    }
}
