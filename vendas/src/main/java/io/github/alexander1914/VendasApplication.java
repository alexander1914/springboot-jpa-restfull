package io.github.alexander1914;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.repository.ClienteRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

//TODO: @SpringBootApplication: para definir a class main para o spring.

//TODO: @ComponentScan: é utiliza para scanear o componentes de configuração,
// services e repositories do spring.
@SpringBootApplication
public class VendasApplication {

    //TODO: Implemetando o JPA
    @Bean
    public CommandLineRunner init(@Autowired ClienteRepositoryJpa clienteRepositoryJpa){
        return args -> {
            System.out.println("Salvando clientes ...");
            clienteRepositoryJpa.save(new Cliente(1,"Alexander"));
            clienteRepositoryJpa.save(new Cliente(2,"Outro Cliente"));

            List<Cliente> todosClientes = clienteRepositoryJpa.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes ...");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                clienteRepositoryJpa.save(c);
            });

            todosClientes = clienteRepositoryJpa.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes ...");
            clienteRepositoryJpa.findByNomeLike("Cli").forEach(System.out::println);

            System.out.println("Deletando clientes ...");
            clienteRepositoryJpa.findAll().forEach(c -> {
                clienteRepositoryJpa.delete(c);
            });

            todosClientes = clienteRepositoryJpa.findAll();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        //TODO: Implementando a configuração para aplicação spring boot com objeto SpringApplication.
        SpringApplication.run(VendasApplication.class);
    }
}