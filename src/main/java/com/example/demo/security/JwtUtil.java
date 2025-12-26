package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret:MySuperSecretKeyForJwtToken1234567890}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long expirationMillis;

    private SecretKey secretKey;

    public void initKey() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private SecretKey getKey() {
        if (secretKey == null) initKey();
        return secretKey;
    }

    // ------------------- TOKEN GENERATION -------------------
    public String generateToken(Map<String,Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    public String generateTokenForUser(UserAccount user) {
        Map<String,Object> claims = Map.of(
                "role", user.getRole(),
                "userId", user.getId()
        );
        return generateToken(claims, user.getEmail());
    }

    // ------------------- PARSING -------------------
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token);
    }

    public Claims extractAllClaims(String token) {
        return parseToken(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token) {
        Object v = extractAllClaims(token).get("userId");
        return v == null ? null : Long.parseLong(v.toString());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ------------------- VALIDATION -------------------
    public boolean isTokenValid(String token, String email) {
        return email.equals(extractUsername(token)) && !isExpired(token);
    }

    public boolean validateToken(String token, String username) {
        return isTokenValid(token, username);
    }
}
