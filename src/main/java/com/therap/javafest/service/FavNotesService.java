package com.therap.javafest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.therap.javafest.model.FavNotes;
import com.therap.javafest.model.Notes;
import com.therap.javafest.repository.FavNotesRepository;
import com.therap.javafest.repository.NotesRepository;
import com.therap.javafest.security.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavNotesService {
    
    private final FavNotesRepository favNotesRepository;
    private final NotesRepository notesRepository;
    private final SessionService sessionService;
    public List<Notes> getFavoriteNotesByEmail() {
        String email = sessionService.getEmail();
        // Get all favorite folder_ids for the user
        List<FavNotes> favNotes = favNotesRepository.findByEmail(email);
        
        // Extract folder_ids
        List<String> folderIds = favNotes.stream()
                .map(FavNotes::getFolder_id)
                .collect(Collectors.toList());
        
        // Get all notes that match these folder_ids
        return notesRepository.findByFolder_idIn(folderIds);
    }
    
    public FavNotes addToFavorites(String folder_id) {
        // Check if already exists
        String email = sessionService.getEmail();
        if (favNotesRepository.existsByEmailAndFolder_id(email, folder_id)) {
            throw new RuntimeException("This note is already in favorites");
        }
    
        FavNotes favNote = new FavNotes(email, folder_id);
        return favNotesRepository.save(favNote);
    }
    
    public boolean removeFromFavorites(String folder_id) {
        String email = sessionService.getEmail();

        if (!favNotesRepository.existsByEmailAndFolder_id(email, folder_id)) {
            throw new RuntimeException("This note is not in favorites");
        }
        
        favNotesRepository.deleteByEmailAndFolder_id(email, folder_id);
        return true;
    }
    
   
}