package com.bookpoint.pedido.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookpoint.pedido.dto.EstadoPedidoDTO;
import com.bookpoint.pedido.dto.PedidoDTO;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.service.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService service;

    @PostMapping
    public Pedido crearPedido(
            @RequestBody PedidoDTO request) {
        return service.crearPedido(request);
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return service.listarPedidos();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(
            @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> historialUsuario(
            @PathVariable Long usuarioId) {
        return service.historialUsuario(
                usuarioId
        );
    }

    @PutMapping("/{id}/estado")
    public Pedido actualizarEstado(
            @PathVariable Long id,
            @RequestBody
            EstadoPedidoDTO request) {
        return service.actualizarEstado(
                id,
                request
        );
    }
}
