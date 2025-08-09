package com.therap.javafest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.therap.javafest.api.AgentApiClient;
import com.therap.javafest.dto.ChatRequest;
import com.therap.javafest.dto.PublicApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final AgentApiClient agentApiClient;

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest request) {
        try {
            Map<String, Object> result = agentApiClient.chat(
                request.getQuery(), 
                request.getThread_id()
            );
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new PublicApiResponse(false, "Failed to send chat message: " + e.getMessage()));
        }
    }
}
