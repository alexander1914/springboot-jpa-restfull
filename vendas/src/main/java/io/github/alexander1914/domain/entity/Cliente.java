package io.github.alexander1914.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

//TODO: @Entity é uma anotation para mapear a tabela como uma entidade.
//TODO: @Table é uma anotation para marcar a entidade e para fazer as definiçoes dela.

//TODO: Lombok: é utilizado para facilitar a criação dos getters e setters,
// para quando buildar a classe gere eles.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table( name = "cliente" )
public class Cliente {

    //TODO: @Id é uma anotation é obrigatória para uma entidade.
    //TODO: @GeneratedValue é uma anotation para definir a strategy da entidade.
    //TODO: @Column é uma anotation para definiçoes da coluna.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "cpf", length = 11)
    private String cpf;

    //TODO: OneToMany: é uma anotation para definir o mapeamento entre as entidades, um para muitos.
    // OBS: a propriedade mappedBy para mapear com a outra entidade.
    //TODO: @JsonIgnore é uma anotation para remover do objeto.
    @JsonIgnore
    @OneToMany( mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    public Cliente(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }
}
