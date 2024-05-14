package io.github.alexander1914.rest.controller;

import io.github.alexander1914.domain.entity.Produto;
import io.github.alexander1914.domain.repository.ProdutosRepositoryJpa;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//TODO: Implementando um import static para simplificar o uso do objeto HTTP.
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutosRepositoryJpa produtosRepositoryJpa;

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")
    })
    public Produto findByIdProduto(@PathVariable @ApiParam("Id do produto") Integer id) {
        return produtosRepositoryJpa
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto nao encontrado."));
    }

    @GetMapping
    @ApiOperation("Obter todos os produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado no filtro"),
            @ApiResponse(code = 404, message = "Produto não encontrado pelo filtro")
    })
    public List<Produto> findAllProdutos(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtosRepositoryJpa.findAll(example);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar um produto para o cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Produto save(@RequestBody
                        @Valid Produto produto) {
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.

        return produtosRepositoryJpa.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atulizar os detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")
    })
    public void update(@PathVariable
                       @ApiParam("Id do produto")
                       Integer id,
                       @RequestBody
                       @Valid Produto produto) {
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.
        produtosRepositoryJpa
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtosRepositoryJpa.save(produto);
                    return produto;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto nao encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletar um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado para deletar")
    })
    public void delete(@PathVariable
                       @ApiParam("Id do produto")
                       Integer id) {
        produtosRepositoryJpa
                .findById(id)
                .map(p -> {
                    produtosRepositoryJpa.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto nao encontrado."));
    }

}
