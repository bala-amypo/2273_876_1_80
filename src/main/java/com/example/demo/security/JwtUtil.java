package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret:MySuperSecretKeyForJwtToken123456}")   // fallback default if not in properties
    private String secret;

    @Value("${jwt.expiration:3600000}") // 1 hour default
    private long expirationMillis;

    private Key secretKey;

    /**
     * Called in tests and app startup
     */
    public void initKey() {
        this.secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private Key getKey() {
        if (secretKey == null) {
            initKey();
        }
        return secretKey;
    }

    // ---------------- TOKEN GENERATION ----------------

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    /**
     * Used in tests
     */
    public String generateTokenForUser(UserAccount user) {
        Map<String, Object> claims = Map.of(
                "role", user.getRole(),
                "userId", user.getId()
        );
        return generateToken(claims, user.getEmail());
    }

    /**
     * Old style API usage (jjwt 0.9.x)
     */
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token);
    }

    // ---------------- CLAIM HELPERS ----------------

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
        Object value = extractAllClaims(token).get("userId");
        if (value == null) return null;
        return Long.parseLong(value.toString());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ---------------- VALIDATION ----------------

    public boolean isTokenValid(String token, String email) {
        String username = extractUsername(token);
        return username != null && username.equals(email) && !isTokenExpired(token);
    }

    public boolean validateToken(String token, String username) {
        return isTokenValid(token, username);
    }
}
