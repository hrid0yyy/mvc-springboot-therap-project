package com.therap.javafest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.therap.javafest.model.FavNotes;

public interface FavNotesRepository extends MongoRepository<FavNotes, String> {
    @Query("{ 'email': ?0 }")
    List<FavNotes> findByEmail(String email);
    
    @Query("{ 'email': ?0, 'folder_id': ?1 }")
    Optional<FavNotes> findByEmailAndFolder_id(String email, String folder_id);
    
    @Query(value = "{ 'email': ?0, 'folder_id': ?1 }", delete = true)
    void deleteByEmailAndFolder_id(String email, String folder_id);
    
    @Query(value = "{ 'email': ?0, 'folder_id': ?1 }", exists = true)
    boolean existsByEmailAndFolder_id(String email, String folder_id);
}