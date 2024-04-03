package io.github.alexander1914.service;

import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
}
