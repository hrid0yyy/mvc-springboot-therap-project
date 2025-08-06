package com.therap.javafest.dto;

import java.util.List;

import lombok.Data;

@Data
public class NotesUploadRequest {
    private String email;
    private String title;
    private String subject;
    private String courseCode;
    private List<String> tags;
    private String description;
}
