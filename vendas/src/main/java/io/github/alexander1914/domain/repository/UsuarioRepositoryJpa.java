package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLogin(String user);
}
