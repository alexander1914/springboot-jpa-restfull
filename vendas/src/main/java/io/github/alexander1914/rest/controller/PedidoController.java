package io.github.alexander1914.rest.controller;

import io.github.alexander1914.domain.entity.ItemPedido;
import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.domain.enums.StatusPedido;
import io.github.alexander1914.dto.AtualizacaoStatusPedidoDTO;
import io.github.alexander1914.dto.InformacoesItemPedidoDTO;
import io.github.alexander1914.dto.InformacoesPedidoDTO;
import io.github.alexander1914.dto.PedidoDTO;
import io.github.alexander1914.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.*;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Integer save(@RequestBody
                        @Valid PedidoDTO pedidoDTO) {
        //TODO: @Valid: é uma anotation do spring para fazer as validações de acordo como foi definido pela sua Entity.
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado")
    })
    public InformacoesPedidoDTO getById(@PathVariable
                                        @ApiParam("Id do pedido")
                                        Integer id) {
        return pedidoService.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    //TODO: @PatchMapping: é uma anotation do spring para atualizar apenas uma parte do codigo do seu projeto.
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar o status de um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "O status do pedido atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado para atualizar o status")
    })
    public void updateStatus(@PathVariable
                             @ApiParam("Id do pedido")
                             Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO pedidoDTO) {
        String novoStatus = pedidoDTO.getNovoStatus();
        pedidoService.AtualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                //TODO: DateTimeFormatter: é objeto serve para passar o formato da data que você precisa.
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacoesItemPedidoDTO.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitatio(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
