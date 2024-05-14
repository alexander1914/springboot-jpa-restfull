package io.github.alexander1914.rest.controller;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.repository.ClientesRepositoryJpa;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
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

    //TODO: @Api é uma anotation para implementar o swagger em seu serviço
    //TODO: ApiOperation: é uma anotation para descrever a função do método
    //TODO: ApiResponses: é uma anotation para definir os codigos e mensagens de status
    //TODO: ApiResponse: é uma anotation para definir o codigo e a mensagem de status de response para o usuário
    //TODO: ApiParam: é uma anotation para colocar uma descrição do paramêtro

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente getClienteById(@PathVariable @ApiParam("Id do cliente") Integer id) {
        //TODO: ResponseEntity: é um objeto que representa o corpo da resposta HTTP.
        return clientesRepositoryJpa.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente nao encontrado"));
    }

    @GetMapping
    @ApiOperation("Obter todos os clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado no filtro"),
            @ApiResponse(code = 404, message = "Cliente não encontrado pelo filtro")
    })
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
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente save(@RequestBody
                        @Valid Cliente cliente) {
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.

        return clientesRepositoryJpa.save(cliente);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar os dados do cliente já existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public void update(@PathVariable Integer id,
                       @RequestBody
                       @Valid Cliente cliente) {
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.

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
    @ApiOperation("Deletar um cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public void delete(@PathVariable
                       @ApiParam("Id do cliente")
                       Integer id) {
        clientesRepositoryJpa.findById(id)
                .map(cliente -> {
                    clientesRepositoryJpa.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT,
                        "Cliente nao encontrado"));
    }
}
