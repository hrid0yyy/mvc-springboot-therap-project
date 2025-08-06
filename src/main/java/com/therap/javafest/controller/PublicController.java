package com.therap.javafest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.therap.javafest.dto.AuthRequest;
import com.therap.javafest.dto.AuthResponse;
import com.therap.javafest.dto.PublicApiResponse;
import com.therap.javafest.model.User;
import com.therap.javafest.security.JwtUtil;
import com.therap.javafest.service.UserDetailsServiceImpl;
import com.therap.javafest.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    private UserService studentService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new PublicApiResponse(false, "Invalid email or password"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token, true));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createStudentAccount(@Valid @RequestBody User student) {
        try {
            studentService.createAccount(student.getEmail(), student.getPassword());
            return ResponseEntity.ok(new PublicApiResponse(true, "Verification email sent. Please check your inbox."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new PublicApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String id, @RequestParam String token) {
        try {
            // Lookup token in DB
            boolean success = studentService.getVerified(id, token);
            // If valid, activate user account
            if(success) {
                return ResponseEntity.ok("Your email has been verified successfully. Go to login page");
            } else {
                return ResponseEntity.badRequest().body(new PublicApiResponse(false, "Invalid verification token"));
            }
           
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

  
    // For tesing OAuth2 in frontend
    @GetMapping("/oauth2/redirect")
    public ResponseEntity<?> success(@RequestParam String token) {
        return ResponseEntity.ok(new AuthResponse(token, true));
    }

  

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PublicApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.badRequest().body(new PublicApiResponse(false, errors));
    }
}