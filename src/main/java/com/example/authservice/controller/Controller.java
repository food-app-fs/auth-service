package com.example.authservice.controller;


import com.example.authservice.model.Auth;
import com.example.authservice.model.UserCredential;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

    @Autowired
    AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return authService.addUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody Auth auth){
       Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPwd()));
       if(authenticate.isAuthenticated()) {
           return authService.generateToken(auth.getUsername());
       }
       else {
           throw new RuntimeException("user invalid access");
       }
    }

    @PostMapping("/validate")
    public boolean getToken(@RequestParam("token") String token) {
        return authService.validateToken(token);
    }


}
