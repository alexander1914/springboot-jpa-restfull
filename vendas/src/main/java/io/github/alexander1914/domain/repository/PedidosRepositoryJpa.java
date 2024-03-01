package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidosRepositoryJpa extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cLiente);
}
