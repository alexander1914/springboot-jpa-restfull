package io.github.alexander1914.service.impl;

import io.github.alexander1914.domain.repository.PedidosRepositoryJpa;
import io.github.alexander1914.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: @Service: é uma anotation do spring para definir a lógica de negócios para uma service.
//TODO: OBS: onde toda camada das regras de negócios para o serviço.
@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidosRepositoryJpa pedidosRepositoryJpa;
}
