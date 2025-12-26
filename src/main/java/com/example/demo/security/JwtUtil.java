package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // ⚠️ Must be at least 32 characters for HS256
    private static final String SECRET_KEY = "my-super-secret-key-for-jwt-demo-12345";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // =========================
    // PUBLIC METHODS
    // =========================

    public String generateTokenForUser(UserAccount user) {
        return generateToken(
                user.getEmail(),
                user.getRole(),
                user.getId()
        );
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public String extractEmail(String token) {
    return extractUsername(token);
}


    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // =========================
    // INTERNAL HELPERS
    // =========================

    private String generateToken(String username, String role, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
