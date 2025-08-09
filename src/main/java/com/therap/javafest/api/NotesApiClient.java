package com.therap.javafest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesApiClient {
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${app.fastapi.url}")
    private String notesApiUrl;

    public Map<String, Object> uploadNotes(List<MultipartFile> files, 
                                         String email, 
                                         String title, 
                                         String subject, 
                                         String courseCode, 
                                         List<String> tags, 
                                         String description) {
        try {
            String url = notesApiUrl + "/notes";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            
            // Add files
            for (MultipartFile file : files) {
                body.add("files", file.getResource());
            }
            
            // Add form data
            body.add("email", email);
            body.add("title", title);
            body.add("subject", subject);
            body.add("course_code", courseCode);
            body.add("description", description);
            
            // Add tags
            for (String tag : tags) {
                body.add("tags", tag);
            }
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to upload notes: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error uploading notes: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> deleteNotes(String folderId) {
        try {
            String url = notesApiUrl + "/notes";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("folder_id", folderId);
            
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url, 
                HttpMethod.DELETE, 
                requestEntity, 
                Map.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to delete notes: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting notes: " + e.getMessage(), e);
        }
    }
}
