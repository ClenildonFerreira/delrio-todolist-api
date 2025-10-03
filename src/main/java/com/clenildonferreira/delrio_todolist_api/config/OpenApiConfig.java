package com.clenildonferreira.delrio_todolist_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("DelRio TodoList API")
                        .description("API para gerenciamento de tarefas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Clenildon Ferreira")
                                .email("clenildonferreira34@gmail.com"))
                        .license(new License().name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do projeto")
                        .url("https://github.com/clenildonferreira/delrio-todolist-api"));
    }
}