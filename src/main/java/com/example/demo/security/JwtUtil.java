package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    // ===================== TOKEN GENERATION =====================
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // Test files & AuthController expect this
    public String generateTokenForUser(com.example.demo.entity.UserAccount user) {
    String identifier = user.getEmail();   // your entity supports this
    return generateToken(identifier);
}


    // ===================== TOKEN PARSING =====================
    private Claims extractAllClaims(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)     // MUST be SecretKey (fixed)
                .build()
                .parseSignedClaims(token);

        return jws.getPayload();     // 0.12.x uses getPayload()
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
