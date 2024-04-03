package io.github.alexander1914.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO: DTO: DATA TRANSFER OBJECT é utilizado para mepear os dados via uma requisição.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {
    private Integer produto;
    private Integer quantidade;
}
