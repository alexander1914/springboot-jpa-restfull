package io.github.alexander1914.controller;

import io.github.alexander1914.domain.entity.Produto;
import io.github.alexander1914.domain.repository.ProdutosRepositoryJpa;
import net.bytebuddy.asm.Advice;
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
    public Produto findByIdProduto(@PathVariable Integer id){
        return produtosRepositoryJpa
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto nao encontrado."));
    }

    @GetMapping
    public List<Produto> findAllProdutos(Produto filtro){
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
    public Produto save(@RequestBody
                            @Valid Produto produto){
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.

        return produtosRepositoryJpa.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody
                       @Valid Produto produto){
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
    public void delete(@PathVariable Integer id){
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
