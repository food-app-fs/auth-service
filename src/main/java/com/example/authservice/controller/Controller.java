package com.example.authservice.controller;


import com.example.authservice.model.Auth;
import com.example.authservice.model.UserCredential;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth/")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {


    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "CRED";

    private long uuid;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository credential;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return authService.addUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody Auth auth) throws Exception {
       Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPwd()));
       if(authenticate.isAuthenticated()) {

           try{
               String username = auth.getUsername();
               uuid = credential.findByEmail(username).getUserid();

               try{
                   String uuidString = String.valueOf(uuid);
                   if(uuidString!=null || uuidString.isEmpty()){
                       redisTemplate.opsForHash().put(KEY,uuidString,uuidString);
                   }else{
                       throw new Exception("UUID EMPTY");
                   }
               }catch (Exception e){
                   throw new Exception(e);
               }

           }catch (Exception e){
              throw new Exception(e);
           }

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

    @GetMapping("/redis")
    public List redis(){
        return redisTemplate.opsForHash().values(KEY);
    }

    @GetMapping("/logout")
    public boolean logout(){
        try {

            redisTemplate.delete(KEY);
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


}
