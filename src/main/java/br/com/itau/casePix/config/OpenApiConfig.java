package br.com.itau.casePix.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Case Itaú - Gerenciamento de chaves Pix")
                        .description("Documentação da API para gerenciamento de chaves Pix")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Julia Andrade Dias")
                                .url("https://github.com/Jujuad")
                                .email("juandradedias1@gmail.com"))
                );
    }
}
