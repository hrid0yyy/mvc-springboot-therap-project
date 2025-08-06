package com.therap.javafest.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.therap.javafest.model.FavNotes;
import com.therap.javafest.model.Notes;
import com.therap.javafest.service.FavNotesService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavNotesGraphQLController {
    
    private final FavNotesService favNotesService;
    
    @QueryMapping
    public List<Notes> getFavoriteNotes(@Argument String email) {
        return favNotesService.getFavoriteNotesByEmail(email);
    }
    
    
    
    @MutationMapping
    public FavNotes addToFavorites(@Argument String email, @Argument String folder_id) {
        return favNotesService.addToFavorites(email, folder_id);
    }
    
    @MutationMapping
    public Boolean removeFromFavorites(@Argument String email, @Argument String folder_id) {
        return favNotesService.removeFromFavorites(email, folder_id);
    }
}