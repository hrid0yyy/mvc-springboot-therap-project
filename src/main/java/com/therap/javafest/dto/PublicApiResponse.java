package com.therap.javafest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicApiResponse {
    private boolean success;
    private String message;
}