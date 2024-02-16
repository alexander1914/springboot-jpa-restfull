package io.github.alexander1914.config;

import io.github.alexander1914.Development;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//TODO: @Configuration: é uma anotation do springboot para definir os bens de configuração.
//TODO: @Profile: é uma anotation para definir o ambiente.
//TODO: @Development: é uma anotation customizada para o spring
@Development
public class Configurations {
    //TODO: @Bean é uma anotation para criar um objeto Bean de configuração.
    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("Executando o sistema de vendas ...");
        };
    }
}
