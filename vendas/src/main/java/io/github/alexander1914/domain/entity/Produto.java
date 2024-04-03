package io.github.alexander1914.domain.entity;

import lombok.*;

import javax.persistence.*;
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
    private String descricao;
    @Column(name = "preco_unitario")
    private BigDecimal preco;
}
