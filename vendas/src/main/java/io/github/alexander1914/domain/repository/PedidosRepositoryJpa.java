package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidosRepositoryJpa extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cLiente);
    @Query(" select p from Pedido p left join fetch p.itens where p.id =:id ")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
