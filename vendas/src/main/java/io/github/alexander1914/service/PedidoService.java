package io.github.alexander1914.service;

import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.domain.enums.StatusPedido;
import io.github.alexander1914.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void AtualizaStatus(Integer id, StatusPedido statusPedido);
}
