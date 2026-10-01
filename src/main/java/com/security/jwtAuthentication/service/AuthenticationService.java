package com.security.jwtAuthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwtAuthentication.jwt_service.JwtService;
import com.security.jwtAuthentication.model.AuthenticationRequest;
import com.security.jwtAuthentication.model.AuthenticationResponse;
import com.security.jwtAuthentication.model.RegisterRequest;
import com.security.jwtAuthentication.model.Role;
import com.security.jwtAuthentication.model.User;
import com.security.jwtAuthentication.user_repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private  UserRepository userRespo;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest req) {

        var user = User.builder()
        .firstname(req.getFirstname())
        .lastname(req.getLastname())
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword()))
        .role(Role.User)
        .build();


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        var user = userRespo.findByEmail(req.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();

    }

}
