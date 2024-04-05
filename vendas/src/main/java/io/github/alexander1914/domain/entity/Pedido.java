package io.github.alexander1914.domain.entity;

import io.github.alexander1914.domain.enums.StatusPedido;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TODO: Lombok: é utilizado para facilitar a criação dos getters e setters,
// para quando buildar a classe gere eles.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //TODO: ManyToOne: é uma anotations para o mapeamento de uma forenkeys muitos pedidos para um cliente.
    // OBS: muitos para um 1.

    //TODO: JoinColumn: é uma anotation para mapear as colunas entre as entidades.
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "data_pedido")
    private LocalDate dataPedido;
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    //TODO: @Enumerated: é uma anotation do spring para definir um objeto como enum.
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    //TODO: OneToMany: é uma anotation para definir o mapeamento entre as entidades, um para muitos.
    // OBS: a propriedade mappedBy para mapear com a outra entidade.
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
