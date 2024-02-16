package io.github.alexander1914.service;

import io.github.alexander1914.model.Cliente;
import io.github.alexander1914.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: @Service: é uma anotation para definir a lógica de negócio para spring.
@Service
public class ClientesServices {

    //TODO: @Autowired: é uma anotation para criar uma instância de objeto para o spring.
    // é uma forma para fazer injeção de dependencia para o spring.
    @Autowired
    private ClientesRepository clientesRepository;
    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        ClientesRepository clientesRepository = new ClientesRepository();
        clientesRepository.persistir(cliente);
    }
    public void validarCliente(Cliente cliente){
            //Aplicar as validações
    }
}
