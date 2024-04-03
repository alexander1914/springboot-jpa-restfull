package io.github.alexander1914.service.impl;

import io.github.alexander1914.domain.entity.Cliente;
import io.github.alexander1914.domain.entity.ItemPedido;
import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.domain.entity.Produto;
import io.github.alexander1914.domain.repository.ClientesRepositoryJpa;
import io.github.alexander1914.domain.repository.ItemsPedidoRepositoryJpa;
import io.github.alexander1914.domain.repository.PedidosRepositoryJpa;
import io.github.alexander1914.domain.repository.ProdutosRepositoryJpa;
import io.github.alexander1914.dto.ItemPedidoDTO;
import io.github.alexander1914.dto.PedidoDTO;
import io.github.alexander1914.exception.RegraNegocioException;
import io.github.alexander1914.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO: @Service: é uma anotation do spring para definir a lógica de negócios para uma service.
//TODO: OBS: onde toda camada das regras de negócios para o serviço.
@Service
public class PedidoServiceImpl implements PedidoService {

    //TODO: Implementando as instâncias do obejto repository para fazer transaçoes na base de dados.
    @Autowired
    private PedidosRepositoryJpa pedidosRepositoryJpa;
    @Autowired
    private ClientesRepositoryJpa clientesRepositoryJpa;
    @Autowired
    private ProdutosRepositoryJpa produtosRepositoryJpa;
    @Autowired
    private ItemsPedidoRepositoryJpa itemsPedidoRepositoryJpa;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientesRepositoryJpa
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItems(pedido, pedidoDTO.getItems());
        pedidosRepositoryJpa.save(pedido);
        itemsPedidoRepositoryJpa.saveAll(itemPedidos);
        pedido.setItems(itemPedidos);

        return pedido;
    }

    //TODO: Aplicando os conceitos de SOLID - S uma responsabilidade para cada metódo ou class.
    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> itemsPedidoDTO) {
        if (itemsPedidoDTO.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return itemsPedidoDTO
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepositoryJpa
                            .findById(idProduto)
                            .orElseThrow(() ->
                                    new RegraNegocioException("Código de produto inválido: " + idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
