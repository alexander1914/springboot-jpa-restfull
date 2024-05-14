package io.github.alexander1914.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: @@EnableSwagger2 -> é uma annotation para configuração para a documentação swagger api

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //TODO: Docket -> é responsável por todas as configurações para o swagger
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("io.github.alexander1914.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Vendas API")
                .description("API RESTFull de um projeto de vendas")
                .version("1.0")
                .contact(contact())
                .build();
    }

    private Contact contact(){
        return  new  Contact(
                "Alexander Albuquerque Oliveira",
                "http://github.com/alexander1914",
                "alexander.oliveira99@gmail.com"
        );
    }

    //TODO: Implementando a autorization no swagger
    public ApiKey apiKey(){
        return new ApiKey("JWI", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");

        AuthorizationScope [] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;

        SecurityReference references = new SecurityReference("JWI", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(references);

        return auths;
    }
}
