package io.github.alexander1914.controller;

import io.github.alexander1914.domain.entity.Pedido;
import io.github.alexander1914.dto.PedidoDTO;
import io.github.alexander1914.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO){
         Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }
}
