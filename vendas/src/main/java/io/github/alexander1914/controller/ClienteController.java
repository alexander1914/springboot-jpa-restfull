package io.github.alexander1914.controller;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.repository.ClientesRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {
    @Autowired
    private ClientesRepositoryJpa clientesRepositoryJpa;

    //TODO: @Controller: é uma anotation definir um bean de gerenciamento do spring.
    //TODO: @RequestMapping é uma anotation para definir uma rota para os métodos HTTP.
    //TODO: @PathVariable: é uma anotation para receber dados por paremtro no spring.
    //TODO: @ResponseBody: é uma anotation para definir o corpo da minha resposta.

    //TODO: @GetMapping: é uma anotation definida pelo spring para metodo HTTP com o request mapping.
    //TODO: @PostMapping: é uma anotation definida para criar uma entidade na base dados pelo spring.
    //TODO: @PutMapping: é uma anotation definida para atualizar uma entidade na base dados pelo spring.
    //TODO: @DeleteMapping: é uma anotation definida para remover uma entidade na base de dados pelo spring.
    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        //TODO: ResponseEntity: é um objeto que representa o corpo da resposta HTTP.
        Optional<Cliente> cliente = clientesRepositoryJpa.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente result = clientesRepositoryJpa.save(cliente);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente) {

        return clientesRepositoryJpa
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientesRepositoryJpa.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientesRepositoryJpa.findById(id);

        if (cliente.isPresent()) {
            clientesRepositoryJpa.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("api/clientes")
    public ResponseEntity findFiltro(Cliente filtro) {
        //TODO: ExampleMatcher é um do spring para definir a sua estrategia para a sua pesquisa.
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List<Cliente> lista = clientesRepositoryJpa.findAll(example);
        return ResponseEntity.ok(lista);
    }

}
