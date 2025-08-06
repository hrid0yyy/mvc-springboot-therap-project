package com.therap.javafest.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Document(collection = "universities")
@Data
public class University {
    
    @Id
    private ObjectId id;
    
    @NotBlank(message = "University name is required")
    @Indexed(unique = true)
    private String name;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Website is required")
    private String website;
    
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotBlank(message = "Contact is required")
    private String contact;
    
    @NotBlank(message = "Logo URL is required")
    private String logoUrl;
}
