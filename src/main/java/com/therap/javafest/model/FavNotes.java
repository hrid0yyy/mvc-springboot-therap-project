package com.therap.javafest.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "favNotes")
@Data
@NoArgsConstructor
public class FavNotes {
    @Id
    private ObjectId id;
    
    private String email;
    private String folder_id;
    private LocalDateTime createdAt;
    
    public FavNotes(String email, String folder_id) {
        this.email = email;
        this.folder_id = folder_id;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getId() {
        return id != null ? id.toString() : null;
    }
    
    public String getCreatedAt() {
        return createdAt != null ? createdAt.toString() : null;
    }
}