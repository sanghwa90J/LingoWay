package com.lingoway.lingoway.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}") // application.properties에 정의
    private String secret;

    private Key secretKey;

    private final long validityInMilliseconds = 1000 * 60 * 60 * 3; // 3시간

    private Key key;

    @PostConstruct
    public void init() {
        // ✅ 문자열을 HMAC-SHA 키 객체로 변환
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ JWT 생성
    public String createToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60); // 1시간

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ JWT에서 subject 추출
    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // ✅ Key 객체로 변경
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}