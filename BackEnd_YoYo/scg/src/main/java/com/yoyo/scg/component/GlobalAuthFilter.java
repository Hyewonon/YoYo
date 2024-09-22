//package com.yoyo.scg.component;
//
//import com.yoyo.scg.util.JwtUtil;
//import com.yoyo.scg.util.EncryptionUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import javax.crypto.SecretKey;
//
//@Slf4j
//@Component
//public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {
//
//    private final JwtUtil jwtUtil;
//    private final SecretKey encryptionKey;
//
//    public GlobalAuthFilter(JwtUtil jwtUtil,@Value("${encryption.secret}") String encryptionSecret) {
//        super(Config.class);
//        this.jwtUtil = jwtUtil;
//        this.encryptionKey = EncryptionUtil.getKey(encryptionSecret);
//    }
//
//    public static class Config {
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            String accessToken = jwtUtil.getToken(request, "ACCESS-TOKEN");
//            String refreshToken = jwtUtil.getToken(request, "REFRESH-TOKEN");
//
//            if (accessToken == null) {
////                log.error("Access Token is missing");
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//
//            if (!jwtUtil.isValidAccessToken(accessToken)) {
//                // 만약 Access Token이 만료되었고, Refresh Token이 유효하면 새로운 Access Token 발급
//                if (refreshToken != null && jwtUtil.isValidRefreshToken(refreshToken)) {
//                    String newAccessToken = jwtUtil.generateAccessToken(refreshToken);
//                    ServerHttpRequest modifiedRequest = request.mutate()
//                            .header("ACCESS-TOKEN", newAccessToken)
//                            .build();
//
//                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
//                } else {
////                    log.error("Invalid Access Token and Refresh Token");
//                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return response.setComplete();
//                }
//            }
//
//            try {
//                String phoneNumber = jwtUtil.getPhoneNumberFromToken(accessToken);
//                String encryptedPhoneNumber = EncryptionUtil.encrypt(phoneNumber, encryptionKey);
////                log.info("Encrypted phoneNumber -> {}", encryptedPhoneNumber);
//
//                ServerHttpRequest modifiedRequest = request.mutate()
//                        .header("ENCRYPTED-PHONE-NUMBER", encryptedPhoneNumber)
//                        .header("AUTH", "true")
//                        .build();
//
//                return chain.filter(exchange.mutate().request(modifiedRequest).build())
//                        .then(Mono.fromRunnable(() -> {
////                            log.info("Custom Post Filter: response code -> {}", response.getStatusCode());
//                        }));
//            } catch (Exception e) {
////                log.error("Error encrypting phoneNumber", e);
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//                return response.setComplete();
//            }
//        };
//    }
//}
