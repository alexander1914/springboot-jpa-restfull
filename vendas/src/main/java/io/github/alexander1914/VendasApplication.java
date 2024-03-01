package io.github.alexander1914;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.domain.repository.ClientesRepositoryJpa;
import io.github.alexander1914.domain.repository.PedidosRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TODO: @SpringBootApplication: para definir a class main para o spring.

//TODO: @ComponentScan: é utiliza para scanear o componentes de configuração,
// services e repositories do spring.
@SpringBootApplication
public class VendasApplication {

    //TODO: Implemetando o JPA
    @Bean
    public CommandLineRunner init(
            @Autowired ClientesRepositoryJpa clienteRepositoryJpa,
            @Autowired PedidosRepositoryJpa pedidosRepositoryJpa){
        return args -> {
            System.out.println("Salvando clientes ...");
            Cliente cliente1 = new Cliente(1,"Alexander Pereira Oliveira");
            clienteRepositoryJpa.save(cliente1);

            Cliente cliente2 = new Cliente(2,"Bruna Carlos");
            clienteRepositoryJpa.save(cliente2);

            //TODO: Registrando um pedido
            Pedido p = new Pedido();
            p.setCliente(cliente1);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.TEN);

            pedidosRepositoryJpa.save(p);

            Cliente cliResult = clienteRepositoryJpa.findClienteFetchPedidos(cliente1.getId());
            System.out.println(cliente1);
            System.out.println(cliResult.getPedidos());

            pedidosRepositoryJpa.findByCliente(cliente2).
                    forEach(System.out::println);

            boolean existe = clienteRepositoryJpa.existsByNome(cliente1.getNome());
            System.out.println("Existe essa pessoa com esse nome: " + existe);

            List<Cliente> cliente = clienteRepositoryJpa.findByNomeOrIdOrderById(cliente1.getNome(), cliente1.getId());
            System.out.println("Nome: " + cliente.get(0).getNome());

            cliente = clienteRepositoryJpa.encontrarPorNome(cliente2.getNome());
            System.out.println("Nome: " + cliente.get(0).getNome());

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
            clienteRepositoryJpa.findByNomeLike(cliente1.getNome()).forEach(System.out::println);

            /*System.out.println("Deletando clientes ...");
            clienteRepositoryJpa.findAll().forEach(c -> {
                clienteRepositoryJpa.delete(c);
            });

            todosClientes = clienteRepositoryJpa.findAll();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{
                todosClientes.forEach(System.out::println);
            }*/
        };
    }

    public static void main(String[] args) {
        //TODO: Implementando a configuração para aplicação spring boot com objeto SpringApplication.
        SpringApplication.run(VendasApplication.class);
    }
}