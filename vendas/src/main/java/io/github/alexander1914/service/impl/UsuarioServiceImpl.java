package io.github.alexander1914.service.impl;

import io.github.alexander1914.domain.entity.Usuario;
import io.github.alexander1914.domain.repository.UsuarioRepositoryJpa;
import io.github.alexander1914.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//TODO: UserDetailsService: é uma class que é responsável para carregar as informações da base de dados

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepositoryJpa.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }

        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO: Implementando para buscar na base de dados.
        Usuario usuario = usuarioRepositoryJpa.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados.."));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

        //TODO: Setando manual
        /*
            if (!username.equals("alexander")){
                throw new UsernameNotFoundException("Usuário não encontrado na base.");
            }
            return User
                    .builder()
                    .username("alexander")
                    .password(passwordEncoder.encode("1914"))
                    .roles("USER", "ADMIN")
                    .build();
            }
       */
    }
}
