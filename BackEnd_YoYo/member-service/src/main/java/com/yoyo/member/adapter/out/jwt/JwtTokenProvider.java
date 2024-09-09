package com.yoyo.member.adapter.out.jwt;

import com.yoyo.member.application.port.out.AuthMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberPhoneNumber;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider implements AuthMemberPort {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private long jwtTokenExpirationInMs;
    @Value("${jwt.refresh.expiration.ms}")
    private long refreshTokenExpirationInMs;


    @Override
    public String generateJwtToken(Member.MemberPhoneNumber memberPhoneNumber) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTokenExpirationInMs);

        return Jwts.builder()
                   .setSubject(memberPhoneNumber.getPhoneNumberValue())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    @Override
    public String generateRefreshToken(Member.MemberPhoneNumber memberPhoneNumber) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                   .setSubject(memberPhoneNumber.getPhoneNumberValue())
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
        } catch (ExpiredJwtException ex) {
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token: 지원하지 않는 JWT 토큰일 때 발생하는 예외
        } catch (IllegalArgumentException ex) {
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
        }
        return false;
    }

    @Override
    public MemberPhoneNumber parseMemberIdFromToken(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
        return new Member.MemberPhoneNumber(claims.getSubject());
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
