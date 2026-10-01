package com.security.jwtAuthentication.jwt_service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.jwtAuthentication.config.KeyConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Autowired
    final KeyConfiguration secretKey;

    public String extractUsername(String token) {
        return null;

    }

    private SecretKey setSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // private Claims extractClaims(String token){
    // return Jwts.parser()
    // .verifyWith(setSignInKey())
    // .build()
    // .parseSignedClaims(token)
    // .getPayLoad();
    // }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);

    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(setSignInKey())
                .compact();

    }

    private Claims extractAllClaims(String token) {
        // 1. Parse the token to get the Signed Claims wrapper object
        Jws<Claims> signedClaims = Jwts.parser()
                .verifyWith(setSignInKey())
                .build()
                .parseSignedClaims(token);

        // 2. Pull the payload out of the wrapper safely
        return signedClaims.getPayload();
    }

}
