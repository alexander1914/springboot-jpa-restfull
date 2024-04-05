package io.github.alexander1914.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//TODO: Lombok: é utilizado para facilitar a criação dos getters e setters,
// para quando buildar a classe gere eles.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    //TODO: @NotEmpty: é uma anotation do spring para aplicar validações.
    @NotEmpty(message = "Campo descrição é obrigatório.")
    private String descricao;
    @Column(name = "preco_unitario")
    //TODO: @NotNull: é uma anotation do spring para aplicar as validações.
    @NotNull(message = "Campo preço é obrigatório.")
    private BigDecimal preco;
}
