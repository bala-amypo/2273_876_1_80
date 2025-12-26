package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret:mysecretkey1234567890}")
    private String secret;

    @Value("${jwt.expiration:86400000}")   // 1 day default
    private long expiration;

    private Key key;

    // ---- TEST EXPECTS THIS ----
    public void initKey() {
        byte[] keyBytes = secret.getBytes();
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Key getKey() {
        if (this.key == null) {
            initKey();
        }
        return this.key;
    }

    // ===================== TOKEN GENERATION =====================
    public String generateTokenForUser(UserAccount user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("userId", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    // Legacy support (some tests may call this)
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    // ===================== PARSE & CLAIMS =====================
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token);
    }

    private Claims extractAllClaims(String token) {
        return parseToken(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public Long extractUserId(String token) {
        Object id = extractAllClaims(token).get("userId");
        return id == null ? null : Long.parseLong(id.toString());
    }

    public boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }

    public boolean isTokenValid(String token, String email) {
        String username = extractUsername(token);
        return username != null && username.equals(email) && !isTokenExpired(token);
    }
}
