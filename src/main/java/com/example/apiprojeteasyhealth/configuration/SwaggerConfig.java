package com.example.apiprojeteasyhealth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;





public class SwaggerConfig {
    /*

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.apiprojeteasyhealth.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API de gestion des patients")
                .description("Cette API permet de gérer les patients de notre application")
                .contact(new Contact("John Doe", "", "john.doe@example.com"))
                .version("1.0.0")
                .build();
    }
*/
}
