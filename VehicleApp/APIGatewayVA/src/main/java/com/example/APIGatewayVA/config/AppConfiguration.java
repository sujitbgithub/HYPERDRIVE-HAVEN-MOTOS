package com.example.APIGatewayVA.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p->p.path("/api/user-auth/v1/**")
                        .uri("http://localhost:9001/"))
                .route(p->p.path("/api/user-vehicle/v1/**")
                        .uri("http://localhost:9002/"))
                .route(predicateSpec -> predicateSpec.path("/api/vehicle/v1/**")
                        .uri("http://localhost:9002/"))
                .build();
    }
}
