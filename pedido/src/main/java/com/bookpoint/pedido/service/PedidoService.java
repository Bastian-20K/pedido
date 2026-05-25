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
        String url = "http://localhost:8086/api/v1/carritos/" + request.getCarritoId();
        Map<String, Object> carrito = restTemplate.getForObject(url, Map.class);
        if (carrito == null) {
            throw new RuntimeException(
                    "Carrito no encontrado");
        }
    

        List<Map<String, Object>> detallesMap = (List<Map<String, Object>>) carrito.get("detalleCarrito");

        List<DetallePedido> detalles =
                detallesMap.stream()
                        .map(p -> DetallePedido.builder()
                                .productoId(
                                        Long.valueOf(
                                                p.get("productoId")
                                                        .toString()
                                        )
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
                        .carritoId(request.getCarritoId())
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
