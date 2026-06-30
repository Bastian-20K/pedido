package com.bookpoint.pedido.dto;

import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long usuarioId;

    private Long ventaId;

    private String direccion;

    private List<DetallePedidoDTO> detalleProductos;
}
