package com.therap.javafest.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String query;
    private String thread_id;
}
