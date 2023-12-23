package com.vc.ApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator routesDynamique(
			ReactiveDiscoveryClient rdc,
			DiscoveryLocatorProperties dlp) {
		return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
	}

	@Bean
	RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/api/client/**").uri("lb://CLIENT-SERVICES"))
				.route(r -> r.path("/api/voiture/**").uri("lb://VOITURE-SERVICES"))
				.build();
	}

}
