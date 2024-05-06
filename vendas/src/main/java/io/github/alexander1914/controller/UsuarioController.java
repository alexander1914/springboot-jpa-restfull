package io.github.alexander1914.controller;

import io.github.alexander1914.domain.entity.Usuario;
import io.github.alexander1914.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }
}
