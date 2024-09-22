package com.yoyo.member.adapter.out.jwt;

import com.yoyo.member.application.port.out.AuthMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberId;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider implements AuthMemberPort {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private long jwtTokenExpirationInMs;
    @Value("${jwt.refresh.expiration.ms}")
    private long refreshTokenExpirationInMs;


    @Override
    public String generateJwtToken(Member.MemberId memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTokenExpirationInMs);

        return Jwts.builder()
                   .setSubject(memberId.getMemberId().toString())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    @Override
    public String generateRefreshToken(Member.MemberId memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                   .setSubject(memberId.getMemberId().toString())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    @Override
    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException ex) {
            // Invalid JWT token: 유효하지 않은 JWT 토큰일 때 발생하는 예외
            log.info("Invalid JWT Token");
            return false;
        } catch (ExpiredJwtException ex) {
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
            log.info("Token Expired");
            return false;
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token: 지원하지 않는 JWT 토큰일 때 발생하는 예외
            log.info("Unsupported Token");
            return false;
        } catch (IllegalArgumentException ex) {
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
            log.info("JWT is Empty");
            return false;
        }
    }

    @Override
    public MemberId parseMemberIdFromToken(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
        return new Member.MemberId(Long.parseLong(claims.getSubject()));
    }

    @Override
    public long getExpirationTime(String jwtToken) {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(jwtToken)
                            .getBody();
        return claims.getExpiration().getTime();
    }
}
