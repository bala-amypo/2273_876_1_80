package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                // ✅ ADD SERVER URL HERE
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local Development Server"))

                .info(new Info()
                        .title("Multi-Branch Academic Calendar Harmonizer")
                        .description("Centralized academic calendar platform for managing branches, events, clashes, merges and harmonized schedules.")
                        .version("1.0.0"))

                .addSecurityItem(
                        new SecurityRequirement().addList("Bearer Authentication"))

                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}
