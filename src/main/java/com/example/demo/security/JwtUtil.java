package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret:ThisIsASecretKeyForJwtAuthentication123456}")
    private String secret;

    private SecretKey key;

    // ******** REQUIRED BY TEST ********
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getSigningKey() {
        if (key == null) initKey();
        return key;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    // ******** REQUIRED BY TEST ********
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ******** REQUIRED BY TEST ********
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // ******** REQUIRED BY TEST ********
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ******** REQUIRED ********
    public String generateTokenForUser(UserAccount user) {
        return generateToken(Map.of(
                "role", user.getRole(),
                "userId", user.getId()
        ), user.getEmail());
    }

    // ******** REQUIRED BY TEST ********
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hr
                .signWith(getSigningKey())
                .compact();
    }

    // ******** REQUIRED BY TEST ********
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // ******** REQUIRED BY TEST ********
    public Claims parseToken(String token) {
        return extractAllClaims(token);
    }
}
