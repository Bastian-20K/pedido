package com.bookpoint.pedido.dto;

import com.bookpoint.pedido.model.TipoProducto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DetallePedidoDTO {
    private Long productoId;

    private String sku;

    private TipoProducto tipoProducto;

    private String nombreProducto;

    private Integer precio;

    private Integer cantidad;

    private Long sucursalId;
}
