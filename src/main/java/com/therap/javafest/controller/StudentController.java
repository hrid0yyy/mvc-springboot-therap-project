package com.therap.javafest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.therap.javafest.model.User;
import com.therap.javafest.service.UserService;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/hi")
    public ResponseEntity<String> sayHi() {
        // send hi to user
        return ResponseEntity.ok("Hi!");
        
    }

}
