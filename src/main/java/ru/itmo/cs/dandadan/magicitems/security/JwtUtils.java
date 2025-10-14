package ru.itmo.cs.dandadan.magicitems.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Objects;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    @PostConstruct
    private void init() {
        Objects.requireNonNull(secret, "JWT secret must be set");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        if (keyBytes.length < 32) {
            throw new IllegalStateException("JWT secret is too short; must be at least 256 bits (base64)");
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private Key signingKey;

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new java.util.Date(now))
                .expiration(new java.util.Date(now + expirationMs))
                .signWith(signingKey)
                .compact();
    }

    public String validateAndExtractUsername(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(getSignInKey(secret))
                .build()
                .parseSignedClaims(token);

        Claims claims = jws.getPayload();

        String subject = claims.getSubject();
        if (subject == null || subject.isEmpty()) {
            throw new JwtException("Missing subject in JWT");
        }
        return subject;
    }

    private SecretKey getSignInKey(String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
