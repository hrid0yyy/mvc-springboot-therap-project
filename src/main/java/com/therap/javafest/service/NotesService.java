package com.therap.javafest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.therap.javafest.model.Notes;
import com.therap.javafest.repository.NotesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesService {
    
    private final NotesRepository notesRepository;
    
    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }
    
    public List<Notes> getNotesByEmail(String email) {
        return notesRepository.findByEmail(email);
    }
    
   
   
    
   
    public List<Notes> searchNotes(String keyword) {
        return notesRepository.searchByKeyword(keyword);
    }
    
    
}