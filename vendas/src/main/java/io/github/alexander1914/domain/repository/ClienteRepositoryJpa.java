package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO: JpaRepository é uma interface que tem o objeto EntityManager para fazer as transações na base de dados.
// OBS: É necessário passar a Entidade a tabela e tipo do ID.
public interface ClienteRepositoryJpa extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);
}
