package com.therap.javafest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.therap.javafest.model.University;

public interface UniversityRepository extends MongoRepository<University, String> {
    // Additional query methods can be defined here if needed
    
}
