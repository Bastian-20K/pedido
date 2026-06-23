package com.bookpoint.pedido.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {
    private Long productoId;

    private String sku;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipoProducto;

    private String nombreProducto;

    private Integer precio;

    private Integer cantidad;

    private Long sucursalId;
}
