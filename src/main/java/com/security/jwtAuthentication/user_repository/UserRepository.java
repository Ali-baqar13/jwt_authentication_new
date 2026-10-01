package com.security.jwtAuthentication.user_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.security.jwtAuthentication.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);
    
}
