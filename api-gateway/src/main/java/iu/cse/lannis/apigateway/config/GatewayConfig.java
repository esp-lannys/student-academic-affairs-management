package iu.cse.lannis.apigateway.config;

import iu.cse.lannis.apigateway.config.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private AuthFilter authFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder rlb) {
        return rlb.routes()
                .route("auth-server", r -> r.path("/auth/**").uri("lb://auth-server"))
                .route("service-user", r -> r.path("/students/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://service-user")
                )
                .build();
    }
}
