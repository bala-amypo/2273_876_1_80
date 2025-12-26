package com.example.demo.security;

import java.util.function.Function;


import io.jsonwebtoken.Claims;


import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private Key key;

    public void initKey() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key)
                .compact();
    }

    public String generateTokenForUser(UserAccount user) {
        Map<String, Object> claims = Map.of(
                "email", user.getEmail(),
                "role", user.getRole(),
                "userId", user.getId()
        );
        return generateToken(claims, user.getEmail());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    public String extractRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    public Long extractUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    public boolean isTokenValid(String token, String email) {
        String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }
public String extractClaim(String token, Function<Claims, String> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
}

public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
}


    private boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
