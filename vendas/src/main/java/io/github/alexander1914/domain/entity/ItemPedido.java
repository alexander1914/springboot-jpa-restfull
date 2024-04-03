package io.github.alexander1914.domain.entity;

import lombok.*;

import javax.persistence.*;

//TODO: Lombok: é utilizado para facilitar a criação dos getters e setters,
// para quando buildar a classe gere eles.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //TODO: ManyToOne: é uma anotations para o mapeamento de uma forenkeys muitos pedidos para um cliente.
    // OBS: muitos para um 1.

    //TODO: JoinColumn: é uma anotation para mapear as colunas entre as entidades.
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @Column
    private Integer quantidade;
}
