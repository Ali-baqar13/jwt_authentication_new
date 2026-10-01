package com.security.jwtAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwtAuthentication.model.AuthenticationRequest;
import com.security.jwtAuthentication.model.AuthenticationResponse;
import com.security.jwtAuthentication.model.RegisterRequest;
import com.security.jwtAuthentication.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    
    @Autowired
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(service.register(req));

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody AuthenticationRequest req) {
        return ResponseEntity.ok(service.authenticate(req));

    }
    
}
