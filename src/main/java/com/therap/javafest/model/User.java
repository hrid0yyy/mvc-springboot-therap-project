package com.therap.javafest.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@NoArgsConstructor
@Data
public class User {
    // Identity fields
    @Id
    private ObjectId id;
    
    @Indexed(unique = true)
    @NonNull
    @Email(message = "Email should be valid")
    private String email;
    
    @NonNull
    private String password;
    
    // Profile fields
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bio;
    
    // Role and verification
    private String role;
    private boolean isVerified;
    private String verificationCode;
    
    // Academic info
    @DBRef
    private University university;
    private String department;
    
    // Metadata
    private LocalDateTime createdAt;
}
