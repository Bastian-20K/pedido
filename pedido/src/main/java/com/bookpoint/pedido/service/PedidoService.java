package com.bookpoint.pedido.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookpoint.pedido.dto.EstadoPedidoDTO;
import com.bookpoint.pedido.dto.PedidoDTO;
import com.bookpoint.pedido.model.DetallePedido;
import com.bookpoint.pedido.model.EstadoPedido;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repository;


    public Pedido crearPedido(PedidoDTO request) {
        

        List<DetallePedido> detalles =
                request.getDetalleProductos()
                        .stream()
                        .map(p -> DetallePedido.builder()
                                .productoId(p.getProductoId())
                                .sku(p.getSku())
                                .tipoProducto(p.getTipoProducto())
                                .nombreProducto(p.getNombreProducto())
                                .precio(p.getPrecio())
                                .cantidad(p.getCantidad())
                                .sucursalId(p.getSucursalId())
                                .build())
                        .toList();

        Pedido pedido =
                Pedido.builder()
                        .usuarioId(request.getUsuarioId())
                        .ventaId(request.getVentaId())
                        .direccion(request.getDireccion())
                        .detalleProductos(detalles)
                        .estado(
                                EstadoPedido.EN_PREPARACION
                        )
                        .fechaPedido(LocalDateTime.now())
                        .build();
        return repository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido no encontrado"));
    }

    public List<Pedido> historialUsuario(
            Long usuarioId) {
        return repository.findByUsuarioId(
                usuarioId
        );
    }

    public Pedido actualizarEstado(
            Long pedidoId,
            EstadoPedidoDTO request) {
        Pedido pedido =
                buscarPorId(pedidoId);

        pedido.setEstado(request.getEstado());

        return repository.save(pedido);
    }
}
