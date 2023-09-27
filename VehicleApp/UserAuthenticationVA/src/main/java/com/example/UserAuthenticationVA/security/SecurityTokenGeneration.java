package com.example.UserAuthenticationVA.security;

import com.example.UserAuthenticationVA.domain.User;

import java.util.Map;

public interface SecurityTokenGeneration {
    Map<String, String> generateToken(User user);
}
