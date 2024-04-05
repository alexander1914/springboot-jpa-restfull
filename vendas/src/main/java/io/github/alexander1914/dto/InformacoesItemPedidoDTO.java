package io.github.alexander1914.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesItemPedidoDTO {
    private String descricaoProduto;
    private BigDecimal precoUnitatio;
    private Integer quantidade;
}
