package com.pranesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    @Value("${spring.jwt.secret}")
    String secret;

    public static final long JWT_TOKEN_VALIDITY = 30 * 24 * 60 * 60; //30 days

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        try {
            getClaims(token);

            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }

    }
}
