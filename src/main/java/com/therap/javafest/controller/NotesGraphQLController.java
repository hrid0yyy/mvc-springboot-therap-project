package com.therap.javafest.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.therap.javafest.model.Notes;
import com.therap.javafest.service.NotesService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotesGraphQLController {
    
    private final NotesService notesService;
    

    
    @QueryMapping
    public List<Notes> getNotesByEmail(@Argument String email) {
        return notesService.getNotesByEmail(email);
    }
    
    @QueryMapping
    public List<Notes> searchNotes(@Argument String keyword) {
        return notesService.searchNotes(keyword);
    }
    
   
}