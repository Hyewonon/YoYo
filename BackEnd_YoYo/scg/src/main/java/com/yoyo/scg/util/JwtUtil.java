package com.yoyo.scg.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private long accessTokenExpirationMs;
    @Value("${jwt.refresh.expiration.ms}")
    private long refreshTokenExpirationMs;

    // JWT 유효성 검증
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 memberId 추출
    public String getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        return claims.get("memberId", String.class);
    }
}