package com.therap.javafest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.therap.javafest.api.NotesApiClient;
import com.therap.javafest.dto.NotesDeleteRequest;
import com.therap.javafest.dto.PublicApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {
    
    private final NotesApiClient notesApiClient;

    @PostMapping
    public ResponseEntity<?> uploadNotes(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("subject") String subject,
            @RequestParam("course_code") String courseCode,
            @RequestParam("tags") List<String> tags,
            @RequestParam("description") String description) {
        
        try {
            Map<String, Object> result = notesApiClient.uploadNotes(
                files, email, title, subject, courseCode, tags, description
            );
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new PublicApiResponse(false, "Failed to upload notes: " + e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNotes(@RequestBody NotesDeleteRequest request) {
        try {
            Map<String, Object> result = notesApiClient.deleteNotes(request.getFolderId());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new PublicApiResponse(false, "Failed to delete notes: " + e.getMessage()));
        }
    }
}
