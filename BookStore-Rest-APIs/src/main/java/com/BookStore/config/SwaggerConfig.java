package com.BookStore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI bookStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Store API")
                        .description("API documentation for the Book Store project")
                        .version("1.0"));
    }
}
