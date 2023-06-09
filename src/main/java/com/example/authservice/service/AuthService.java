package com.example.authservice.service;


import com.example.authservice.model.UserCredential;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    public String addUser(UserCredential user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "success";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }






}
