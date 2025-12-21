package com.example.onlineeducationplatform.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Collections;

public class JwtUtil {
    // In production, move to configuration and rotate regularly
    private static final String SECRET_KEY = "secretKey123456";
    private static final long EXPIRATION_MS = 10L * 365 * 24 * 60 * 60 * 1000; // 10 years

    public static String generateToken(String username, List<String> roles) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsername(String token) {
        try {
            return parseToken(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> getRoles(String token) {
        try {
            Object claim = parseToken(token).get("roles");
            if (claim instanceof List) {
                return (List<String>) claim;
            }
        } catch (Exception ignored) {
        }
        return Collections.emptyList();
    }
}
