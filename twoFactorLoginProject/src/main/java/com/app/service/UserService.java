package com.app.service;

import java.io.UnsupportedEncodingException;

import com.app.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    String generateQRUrl(User user) throws UnsupportedEncodingException;

    
}