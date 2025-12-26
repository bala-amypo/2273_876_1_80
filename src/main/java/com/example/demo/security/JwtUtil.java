package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private SecretKey key;
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    // ================= INIT =================
    @PostConstruct
    public void initKey() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // ================= GENERATE TOKEN =================
    public String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // Tests call this
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // Tests call this
    public String generateTokenForUser(com.example.demo.entity.UserAccount user) {
        return generateToken(user.getEmail());
    }

    // ================= PARSE TOKEN =================
    public Claims parseToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        return jws.getPayload();   // IMPORTANT for 0.12.x
    }

    private Claims extractAllClaims(String token) {
        return parseToken(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ================= EXTRA METHODS TESTS EXPECT =================
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Object value = claims.get("userId");

        if (value == null) return null;

        if (value instanceof Integer) return ((Integer) value).longValue();
        if (value instanceof Long) return (Long) value;

        return Long.parseLong(value.toString());
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        Object value = claims.get("role");
        return value != null ? value.toString() : null;
    }

    // ================= VALIDATION =================
    public boolean isTokenValid(String token, String username) {
        String extracted = extractUsername(token);
        return extracted != null
                && extracted.equals(username)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
