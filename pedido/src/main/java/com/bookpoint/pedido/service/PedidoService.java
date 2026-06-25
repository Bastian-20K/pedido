package com.bookpoint.pedido.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookpoint.pedido.dto.EstadoPedidoDTO;
import com.bookpoint.pedido.dto.PedidoDTO;
import com.bookpoint.pedido.model.DetallePedido;
import com.bookpoint.pedido.model.EstadoPedido;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.model.TipoProducto;
import com.bookpoint.pedido.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repository;

    private final RestTemplate restTemplate;

    public Pedido crearPedido(PedidoDTO request) {
        String url =
                "http://localhost:8085/api/v1/ventas/"
                + request.getVentaId();


        Map<String,Object> venta =
                restTemplate.getForObject(
                        url,
                        Map.class
                );


        if (venta == null) {
                throw new RuntimeException(
                "Venta no encontrada"
                );
        }


        List<Map<String,Object>> detallesMap =
                (List<Map<String,Object>>)
                        venta.get("detalleProductos");

        List<DetallePedido> detalles =
                detallesMap.stream()
                        .map(p -> DetallePedido.builder()
                                .productoId(
                                        Long.valueOf(
                                                p.get("productoId")
                                                        .toString()
                                        )
                                )
                                .sku(
                                        p.get("sku")
                                                .toString()
                                )
                                .tipoProducto(
                                        TipoProducto.valueOf(
                                                p.get("tipoProducto")
                                                        .toString()
                                        )
                                )
                                .nombreProducto(
                                        p.get("nombreProducto")
                                                .toString()
                                )
                                .precio(
                                        Integer.valueOf(
                                                p.get("precio")
                                                        .toString()
                                        )
                                )
                                .cantidad(
                                        Integer.valueOf(
                                                p.get("cantidad")
                                                        .toString()
                                        )
                                )
                                .sucursalId(
                                        Long.valueOf(
                                                p.get("sucursalId")
                                                        .toString()
                                        )
                                )
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
