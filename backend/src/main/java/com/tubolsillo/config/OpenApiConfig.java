package com.tubolsillo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * Clase de configuración de OpenApi
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configuración del OpenApi
     *
     * @return OpenApi configurado
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend TuBolsillo")
                        .version("1.0.0")
                        .description("API que comunica el backend con el frontend del sistema de TuBolsillo")
                        .contact(new Contact()
                                .name("Gabriel Rincón López")
                                .email("gabrielrl2004@gmail.com")
                                .url("https://www.gabrirl.dev/")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(new ArrayList<>() {{
                    add(new Server()
                            .url("http://localhost:8080")
                            .description("DEV SERVER"));
                }})
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
