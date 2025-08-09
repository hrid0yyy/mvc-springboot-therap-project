package com.therap.javafest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.therap.javafest.model.Notes;
import com.therap.javafest.repository.FavNotesRepository;
import com.therap.javafest.repository.NotesRepository;
import com.therap.javafest.security.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesService {
    
    private final NotesRepository notesRepository;
    private final FavNotesRepository favNotesRepository;
    private final SessionService sessionService;
    public List<Notes> getNotesByEmail(String email) {
        List<Notes> notes =notesRepository.findByEmail(email);

          
    return notes.stream()
        .map(note -> {
            boolean isFavorite = favNotesRepository.existsByEmailAndFolder_id(email, note.getFolder_id());
            note.setFavorite(isFavorite); 
            return note;
        })
        .collect(Collectors.toList());
    }
    
   

    public List<Notes> searchNotes(String keyword) {
        List<Notes> notes = notesRepository.searchByKeyword(keyword);
    
    return notes.stream()
        .map(note -> {
            String email = sessionService.getEmail();
            boolean isFavorite = favNotesRepository.existsByEmailAndFolder_id(email, note.getFolder_id());
            note.setFavorite(isFavorite); 
            return note;
        })
        .collect(Collectors.toList());
    }
    
    
}