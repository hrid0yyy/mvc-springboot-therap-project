package com.therap.javafest.model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "notes")
@Data
@NoArgsConstructor
public class Notes {
    @Id
    private ObjectId id;
    
    private String email;
    private String title;
    private String subject;
    private String course_code;
    private List<String> tags;
    private String description;
    private String folder_id;
    private List<String> urls;
    private LocalDateTime createdAt;
    private boolean isFavorite;
    private String score;
    private String feedback;
    
    public String getId() {
        return id != null ? id.toString() : null;
    }
    
    public String getCourse_code() {
        return this.course_code;
    }
    
    public String getFolder_id() {
        return this.folder_id;
    }
    
    public String getCreatedAt() {
        return createdAt != null ? createdAt.toString() : null;
    }
}
