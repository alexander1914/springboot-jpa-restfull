package io.github.alexander1914.controller;

import io.github.alexander1914.dto.ApiErrors;
import io.github.alexander1914.exception.PedidoNaoEncontradoException;
import io.github.alexander1914.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//TODO: @ControllerAdvice: é anatation do spirng  para implementar classes handler para tratamento de exceptions,
// e registrar suas .

//TODO: @RestControllerAdvice: é anotation do spring que a união de RestController com ControllerAdvice.
// OBS: é utilizada para os erros tratar das regras de negocio do seu projeto.
@RestControllerAdvice
public class ApplicationControllerAdvice {

    //TODO: @ExceptionHandler: é uma anotation do spring para definir como handler exception.
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
         String mensagemErro = ex.getMessage();
         return new ApiErrors(mensagemErro);
    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }
}
