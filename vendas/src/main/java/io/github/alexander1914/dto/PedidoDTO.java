package io.github.alexander1914.dto;

import io.github.alexander1914.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

//TODO: DTO: DATA TRANSFER OBJECT é utilizado para mepear os dados via uma requisição.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    //TODO: @NotEmpty: é uma anotation do spring para aplicar validações.
    //TODO: @NotNull: é uma anotation do spring para aplicar as validações.
    @NotNull(message = "Informe o código do cliente.")
    private Integer cliente;
    @NotNull(message = "Campo Total do pedido é obrigatório.")
    private BigDecimal total;
    //TODO: @NotEmptyList: minha anotation customizada o spring tem esse recurso,
    // para nós criamos a nossa própria anotation.
    @NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
    private List<ItemPedidoDTO> itens;
}
