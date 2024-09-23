package com.yoyo.scg.component;

import com.yoyo.scg.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

    private final JwtUtil jwtUtil;

    @Autowired
    public GlobalAuthFilter(JwtUtil jwtUtil) {
        super(GlobalAuthFilter.Config.class);
        this.jwtUtil = jwtUtil;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if(path.equals("/yoyo/payment/success") || path.equals("/confirm/payment") || path.equals("/yoyo/members/**")){
                return chain.filter(exchange);
            }
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith("Bearer ")) {
                return handleUnauthorized(exchange);
            }
            token = token.substring(7); // "Bearer " 이후의 실제 토큰 값 추출

            try {
                // 1. JWT 검증
                if (!jwtUtil.validateJwtToken(token)) {
                    return handleUnauthorized(exchange);
                }
                // 2. JWT에서 memberId 추출
                String memberId = jwtUtil.getMemberIdFromToken(token);
                // 3. 요청 헤더에 memberId 추가
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("memberId", memberId)
                        .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (Exception e) {
                log.error("JWT 검증 실패", e);
                return handleUnauthorized(exchange);
            }
        };
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}