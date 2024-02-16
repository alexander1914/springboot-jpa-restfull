package io.github.alexander1914;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: @SpringBootApplication: para definir a class main para o spring.

//TODO: @ComponentScan: é utiliza para scanear o componentes de configuração,
// serivces e repositories do spring.
@SpringBootApplication
@ComponentScan(basePackages = {"io.github.alexander1914.repository"} )
@RestController
public class VendasApplication {

    //TODO: Testando o bean de configuração pelo arquivo ymal resouce
    @Value("${application.name}")
    private String applicationName;

    //TODO: Implementando um teste com spring boot restfull
    @GetMapping("/hello")
    public String helloSpringBoot(){
        return "Bem-vindo aos estudos com spring boot expert: " + applicationName;
    }
    public static void main(String[] args) {
        //TODO: Implementando a configuração para aplicação spring boot com objeto SpringApplication.
        SpringApplication.run(VendasApplication.class);
    }
}