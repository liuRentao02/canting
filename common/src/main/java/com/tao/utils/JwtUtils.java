package com.tao.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    private static final String FIXED_SECRET = "myFixedSuperSecretKeyForJWT123!@#$%^&*()";
    private SecretKey jwtSecret;
    private static final long jwtExpirationMs = 24 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        log.info("初始化时固定密钥");
        byte[] keyBytes = Base64.getEncoder().encode(FIXED_SECRET.getBytes());
        this.jwtSecret = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(org.springframework.security.core.Authentication authentication) {
        String username = authentication.getName();
        log.info("生成jwt:{}", username);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, Jwts.SIG.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        log.info("传入的token:{}", token);
        return Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseSignedClaims(authToken);
            log.info("jwt密钥验证成功");
            return true;
        } catch (Exception e) {
            log.error("jwt密钥验证失败: {}", e.getMessage());
            return false;
        }
    }
}
