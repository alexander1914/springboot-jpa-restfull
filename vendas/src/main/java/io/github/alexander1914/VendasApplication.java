package io.github.alexander1914;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.repository.ClientesRepository;
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

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientesRepository){
        return args -> {
            System.out.println("Salvando clientes ...");
            clientesRepository.salvar(new Cliente(1,"Alexander"));
            clientesRepository.salvar(new Cliente(2,"Outro Cliente"));

            List<Cliente> todosClientes = clientesRepository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes ...");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                clientesRepository.atualizar(c);
            });

            todosClientes = clientesRepository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes ...");
            clientesRepository.buscarPorNome("Cli").forEach(System.out::println);

            System.out.println("Deletando clientes ...");
              clientesRepository.obterTodos().forEach(c -> {
                clientesRepository.deletar(c);
            });

            todosClientes = clientesRepository.obterTodos();
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