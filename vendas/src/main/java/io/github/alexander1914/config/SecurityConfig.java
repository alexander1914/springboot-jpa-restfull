package io.github.alexander1914.config;

import io.github.alexander1914.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//TODO: @EnableWebSecurity: é uma anotation do spring security para definir as configurações para esse objeto.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO: é para fazer autenticação dos usuários do security
    // Autenticação
    @Bean
    public PasswordEncoder passwordEncoder(){
        //TODO: BCryptPasswordEncoder: é um algoritimo muito avançado em autênticação
        // OBS: ele recebe uma senha e criptografa essa senha, ele sempre cria um hash diferente.
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO: Implementando um configuração básica do objeto AuthenticationManagerBuilder
        // OBS: neste caso em memória.
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }
    //TODO: esse método é para definir toda a parte de autorição
    // Autorização
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //TODO: Implementando um configuração básica do objeto HttpSecurity
        // OBS: Aplicando os controles de acesso.
        httpSecurity
                .csrf().disable()
                    .authorizeRequests()
                .antMatchers("/api/clientes/**")
                    .hasAnyRole("USER", "ADMIN")

                .antMatchers("/api/pedidos/**")
                    .hasRole( "ADMIN")

                .antMatchers("/api/produtos/**")
                    .hasAnyRole("USER", "ADMIN")

                .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                    .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();//TODO: é uma forma simples para acessar por header passandos os parametros.
                //.formLogin(); //TODO: VIA login
    }
}
