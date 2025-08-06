package com.therap.javafest.security;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    
    public String getId() {
        return (String) attributes.get("sub");
    }
    
    public String getName() {
        return (String) attributes.get("name");
    }
    
    public String getEmail() {
        return (String) attributes.get("email");
    }
    
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
