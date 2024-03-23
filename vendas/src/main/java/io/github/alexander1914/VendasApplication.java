package io.github.alexander1914;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: @SpringBootApplication: para definir a class main para o spring.

//TODO: @ComponentScan: é utilizada para scanear o componentes de configuração,
// services e repositories do spring.
@SpringBootApplication
public class VendasApplication {
    public static void main(String[] args) {
        //TODO: Implementando a configuração para aplicação spring boot com objeto SpringApplication.
        SpringApplication.run(VendasApplication.class);
    }
}