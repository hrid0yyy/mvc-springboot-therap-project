package com.therap.javafest.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.therap.javafest.model.User;
import com.therap.javafest.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User createAccount(String email, String password) {
       try{
        if (userRepository.existsByEmail(email) ) {
            if(userRepository.existsByEmailAndIsVerifiedIsTrue(email)){ 
                throw new RuntimeException("Student with this email already exists");
            }
            else{
                userRepository.deleteById(userRepository.findByEmail(email).getId());
            }
        }
        User user = new User();
        String verificationCode = java.util.UUID.randomUUID().toString().substring(0,6);
        user.setVerificationCode(verificationCode);
        user.setVerified(false);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("STUDENT"); 
        user = userRepository.save(user);
        emailService.sendVerificationEmail(email, user.getId()+"", verificationCode);
        return user;
       }
       catch (Exception e) {
           throw new RuntimeException("Error creating account: " + e.getMessage());
       }
    }

    @Transactional
    public boolean getVerified(String id, String token){
        try {
            User user = userRepository.findById(id).orElse(null);
            // check if the token is null 
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            if(user.isVerified()){
                return true; // User already verified
            }
            if (!user.getVerificationCode().equals(token)) {
                return false; // Invalid token
            }
            user.setVerified(true);
            user.setVerificationCode(null); // Clear the verification code after successful verification
            userRepository.save(user);
            return true;
            
        } catch (Exception e) {
            throw new RuntimeException("Error verifying account: " + e.getMessage());
        }

    }

    @Transactional
    public User processOAuthPostLogin(String email, String name) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return existingUser;
        }
        return createOAuthUser(email, name);
    }

    private User createOAuthUser(String email, String name) {
        User newUser = new User();
        String[] names = name.split(" ", 2);
        
        newUser.setEmail(email);
        newUser.setVerified(true);
        newUser.setRole("STUDENT");
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setFirstName(names[0]);
        newUser.setLastName(names.length > 1 ? names[1] : "");
        
        return userRepository.save(newUser);
    }

    public User findById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
