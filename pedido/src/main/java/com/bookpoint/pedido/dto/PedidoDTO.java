package com.bookpoint.pedido.dto;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long usuarioId;

    private Long carritoId;

    private Long ventaId;

    private String direccion;
}
