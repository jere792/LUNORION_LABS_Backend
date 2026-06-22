package com.lunorion.labs.shared.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(String email, String tenantId, String rol, List<String> permisos) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("tenantId", tenantId)
                .claim("rol", rol)
                .claim("permisos", permisos)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getTenantIdFromToken(String token) {
        return parseClaims(token).get("tenantId", String.class);
    }

    public String getRolFromToken(String token) {
        return parseClaims(token).get("rol", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getPermisosFromToken(String token) {
        return parseClaims(token).get("permisos", List.class);
    }

    public long getExpiration() {
        return expiration;
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
