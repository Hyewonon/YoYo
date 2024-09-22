//package com.yoyo.scg.util;
//
//import com.yoyo.scg.util.EncryptionUtil;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//    @Value("${jwt.expiration.ms}")
//    private long accessTokenExpirationMs;
//    @Value("${jwt.refresh.expiration.ms}")
//    private long refreshTokenExpirationMs;
//    @Value("${encryption.secret}")
//    private String encryptionSecret;
//
//    private SecretKey encryptionKey;
//
//    @PostConstruct
//    public void init() {
//        this.encryptionKey = EncryptionUtil.getKey(encryptionSecret);
//    }
//
//    // JWT Access Token 생성
//    public String generateAccessToken(String phoneNumber) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + accessTokenExpirationMs);
//
//        return Jwts.builder()
//                .setSubject(phoneNumber)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String generateRefreshToken(String phoneNumber) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationMs);
//
//        return Jwts.builder()
//                .setSubject(phoneNumber)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public boolean isValidAccessToken(String token) {
//        return !isTokenExpired(token);
//    }
//    public boolean isValidRefreshToken(String token) {
//        return !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return getClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String getPhoneNumberFromToken(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    public String getEncryptedPhoneNumberFromToken(String token) throws Exception {
//        String phoneNumber = getPhoneNumberFromToken(token);  // JWT에서 추출한 전화번호
//        return EncryptionUtil.encrypt(phoneNumber, encryptionKey);  // 전화번호를 암호화해서 반환
//    }
//
//    public String getToken(ServerHttpRequest request, String header) {
//        List<String> token = request.getHeaders().get(header);
//        return (token != null && !token.isEmpty()) ? token.get(0) : null;
//    }
//
//    public String decryptPhoneNumber(String encryptedPhoneNumber) throws Exception {
//        return EncryptionUtil.decrypt(encryptedPhoneNumber, encryptionKey);
//    }
//}
