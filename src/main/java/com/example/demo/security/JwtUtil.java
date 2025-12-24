package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secret = "your-secret-key";
    private final long expirationMs = 86400000; // 1 day

    public String generateToken(Long id, String email, String role) {
        return Jwts.builder()
                .claim("id", id)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Add validateToken and getClaims methods as needed
}
