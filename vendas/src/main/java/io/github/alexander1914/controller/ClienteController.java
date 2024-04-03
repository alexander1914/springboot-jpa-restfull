package io.github.alexander1914.controller;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.repository.ClientesRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClientesRepositoryJpa clientesRepositoryJpa;

    //TODO: @RestController: é uma anotation para definir como rest controller componente.
    // OBS: nao é necessario colocar o response body.
    //TODO: @Controller: é uma anotation para definir um bean de gerenciamento do spring.
    //TODO: @RequestMapping é uma anotation para definir uma rota para os métodos HTTP.
    // OBS: sem precisar colocar a anotation response body porque jà està embutida dentro dessa anotation.
    //TODO: @PathVariable: é uma anotation para receber dados por paremtro no spring.
    //TODO: @ResponseBody: é uma anotation para definir o corpo da minha resposta.

    //TODO: @GetMapping: é uma anotation definida pelo spring para metodo HTTP para fazer buscas com o request mapping.
    //TODO: @PostMapping: é uma anotation definida para criar uma entidade na base dados pelo spring com o request mapping.
    //TODO: @PutMapping: é uma anotation definida para atualizar uma entidade na base dados pelo spring com o request mapping.
    //TODO: @DeleteMapping: é uma anotation definida para remover uma entidade na base de dados pelo spring com o request mapping.
    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        //TODO: ResponseEntity: é um objeto que representa o corpo da resposta HTTP.
        return clientesRepositoryJpa.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente nao encontrado"));
    }

    @GetMapping
    public List<Cliente> findFiltro(Cliente filtro) {
        //TODO: ExampleMatcher é um do spring para definir a sua estrategia para a sua pesquisa.
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clientesRepositoryJpa.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
        return clientesRepositoryJpa.save(cliente);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente) {
        clientesRepositoryJpa
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientesRepositoryJpa.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT,
                        "Cliente nao encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientesRepositoryJpa.findById(id)
                .map(cliente -> { clientesRepositoryJpa.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT,
                        "Cliente nao encontrado"));
    }
}
