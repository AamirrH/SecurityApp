package com.code.security.app.securitywebapp.services;


import com.code.security.app.securitywebapp.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String jwtsecretKey;

    // takes the key and makes it a encoded signature through hmacSha encoding method
    private SecretKey HMACGeneratedKey(){
        return Keys.hmacShaKeyFor(jwtsecretKey.getBytes(StandardCharsets.UTF_8));
    }


    // Generating a JWT Token

    public String generateJWTToken(UserEntity userEntity){
        // builder -> builds the token , parser -> parses/breaks the token
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                /*Claims are basically the payload of the JWT token which contains
                 non-sensitive data */
                .claim("email", userEntity.getEmail())
                .claim("username", userEntity.getUsername())
                .claim("roles",userEntity.getRoles())
                // IAT -> the time in milliseconds when the token was issued
                .issuedAt(new Date(System.currentTimeMillis()))
                // expiration -> the time after which token will get expired
                .expiration(new Date(System.currentTimeMillis()+60*1000))
                // Sign it with the encoded signature using a secret Key
                .signWith(HMACGeneratedKey())
                .compact();

    }

    // Parsing a JWT Token
    // Extracts only the "Subject" from the JWT
    public Long getUserIDFromJWTToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(HMACGeneratedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());

    }


}
