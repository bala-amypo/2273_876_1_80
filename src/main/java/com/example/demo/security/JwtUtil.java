
package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserAccount user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        String tokenUser = extractUsername(token);
        return tokenUser.equals(username) && !isTokenExpired(token);
    }
    // ===== REQUIRED BY TESTS =====

// old test init
public void initKey() {
    getSigningKey();
}

// generate with claims
public String generateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(getSigningKey())
            .compact();
}

// alias to keep test happy
public String generateTokenForUser(UserAccount user) {
    return generateToken(user);
}

public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
}

public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("userId", Long.class));
}

public boolean isTokenValid(String token, String username) {
    return validateToken(token, username);
}

// TEST expects this
public Claims parseToken(String token) {
    return extractAllClaims(token);
}

}
