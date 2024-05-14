package io.github.alexander1914.rest.controller;

import io.github.alexander1914.domain.entity.Usuario;
import io.github.alexander1914.dto.CredenciaisDTO;
import io.github.alexander1914.dto.TokenDTO;
import io.github.alexander1914.exception.SenhaInvalidaException;
import io.github.alexander1914.security.jwt.JwtService;
import io.github.alexander1914.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Usuario salvar(@RequestBody
                          @Valid
                          Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    @ApiOperation("Para criar uma autorização para o usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public TokenDTO autenticar(@RequestBody
                               CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha())
                    .build();

            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
