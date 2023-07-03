package com.example.authservice.repository;

import com.example.authservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCredential,Long> {

   UserCredential findByEmail(String s);

}
