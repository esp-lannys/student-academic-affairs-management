package iu.cse.lannis.apigateway.config;

import iu.cse.lannis.apigateway.exception.JwtTokenMalformedException;
import iu.cse.lannis.apigateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.Claims;
import iu.cse.lannis.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthFilter implements GatewayFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        final List<String> apiEndpoints = List.of("/register", "/login");
        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));
        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            } else {
                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
                try {
                    jwtUtil.validateToken(token);
                } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                    e.printStackTrace();
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                Claims claims = jwtUtil.getClaims(token);
                exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
            }
        }
        return chain.filter(exchange);
    }
}
