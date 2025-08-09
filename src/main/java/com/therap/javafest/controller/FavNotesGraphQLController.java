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
    public List<Notes> getFavoriteNotes() {
        return favNotesService.getFavoriteNotesByEmail();
    }
    
    @MutationMapping
    public FavNotes addToFavorites(@Argument String folder_id) {
        return favNotesService.addToFavorites( folder_id);
    }
    
    @MutationMapping
    public Boolean removeFromFavorites( @Argument String folder_id) {
        return favNotesService.removeFromFavorites( folder_id);
    }
}