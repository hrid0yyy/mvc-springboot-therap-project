package com.therap.javafest.security;

import org.springframework.stereotype.Service;

@Service
public class SessionService {

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * @return the email of the current user
     */
    public String getEmail() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }
}