package com.example.aiupskillbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Upskill Backend API")
                        .version("1.0.0")
                        .description("A minimal Spring Boot REST API with magical greetings")
                        .contact(new Contact()
                                .name("AI Upskill Team")
                                .email("contact@aiupskill.com")));
    }
}
