package io.github.alexander1914.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @NotEmpty(message = "O Campo Login é obrigatório")
    private String login;
    @Column
    @NotEmpty(message = "O Campo Senha é obrigatório")
    private String senha;
    @Column
    private boolean admin;
}
