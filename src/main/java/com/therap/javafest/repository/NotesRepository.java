package com.therap.javafest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.therap.javafest.model.Notes;

public interface NotesRepository extends MongoRepository<Notes, String> {
    List<Notes> findByEmail(String email);
      

    @Query("{ 'folder_id': { $in: ?0 } }")
    List<Notes> findByFolder_idIn(List<String> folderIds);
      
    @Query("{ '$or': [ " +
           "{ 'title': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } }, " +
           "{ 'subject': { $regex: ?0, $options: 'i' } }, " +
           "{ 'course_code': { $regex: ?0, $options: 'i' } }, " +
           "{ 'tags': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Notes> searchByKeyword(String keyword);
}