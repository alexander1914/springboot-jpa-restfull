package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepositoryJpa extends JpaRepository<Produto, Integer> {
}
