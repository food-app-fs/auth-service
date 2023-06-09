package com.example.authservice.config;

import com.example.authservice.model.UserCredential;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserCredential> data = Optional.ofNullable(repo.findByEmail(username));
        return data.map(CustomUserDetailObjects::new).orElseThrow(()->new UsernameNotFoundException("User not found: " + username));
    }
}
