package com.example.todoback.settings;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(security = { @SecurityRequirement(name = "bearerAuth") })
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP, 
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi
                .builder()
                .displayName("App API List")
                
                .packagesToScan("com.example.todoback.controllers") 
                .group("Public API")
                .addOpenApiCustomizer(statusApiCustomizer())
                .build();
    }

    private OpenApiCustomizer statusApiCustomizer() {
        return openAPI -> openAPI
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server")
                ))
                .info(new Info()
                        .title("Esercizio")
                        .description("Platform's main APIs including User authentication, Tasks, and more.")
                        .version("1.0.0") 
                        .contact(new Contact()
                                .name("Alireza Asgari")
                        )
                );
    }
}