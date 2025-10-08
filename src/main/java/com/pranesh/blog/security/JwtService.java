package com.pranesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pranesh.blog.entities.Role;
import com.pranesh.blog.entities.User;
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

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
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

    public Integer getUserIdFromToken(String token) {
        return Integer.valueOf(getClaims(token).getSubject());
    }

    public List<String> getRolesFromToken(String token) {
        return (List<String>) getClaims(token).get("roles");
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
