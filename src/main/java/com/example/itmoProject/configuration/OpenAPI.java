package com.example.itmoProject.configuration;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

//http://localhost:8080/swagger-ui/index.html
@Configuration
@OpenAPIDefinition(info = @Info(title = "ITMO_Project", version = "1.0"))
public class OpenAPI {
}
