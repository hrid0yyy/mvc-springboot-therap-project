package com.therap.javafest.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.therap.javafest.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);  // This returns User directly, not Optional<User>
    boolean existsByEmail(String email);
    boolean existsByEmailAndIsVerifiedIsTrue(String email); 
    void deleteById(ObjectId id);
}
