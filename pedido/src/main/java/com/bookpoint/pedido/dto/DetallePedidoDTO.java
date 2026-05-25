package com.bookpoint.pedido.dto;

import com.bookpoint.pedido.model.TipoProducto;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Long productoId;

    private TipoProducto tipoProducto;

    private String nombreProducto;

    private Integer precio;

    private Long sucursalId;
}
