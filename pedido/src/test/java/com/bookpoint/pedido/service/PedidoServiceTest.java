package com.bookpoint.pedido.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.bookpoint.pedido.dto.EstadoPedidoDTO;
import com.bookpoint.pedido.dto.PedidoDTO;
import com.bookpoint.pedido.model.EstadoPedido;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.repository.PedidoRepository;

public class PedidoServiceTest {
    
    @Mock
    private PedidoRepository repository;


    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    private PedidoService service;


    @BeforeEach
    void setup(){

        MockitoAnnotations.openMocks(this);
        }

    @Test
    void crearPedido_deberiaCrearPedido(){

        PedidoDTO dto = new PedidoDTO();

        dto.setUsuarioId(1L);
        dto.setVentaId(10L);
        dto.setDireccion("Direccion prueba");

        Map<String,Object> venta =
                Map.of(
                        "detalleProductos",
                        java.util.List.of(
                                Map.of(
                                        "productoId",1,
                                        "sku","ABC123",
                                        "tipoProducto","LIBRO",
                                        "nombreProducto","Libro",
                                        "precio",5000,
                                        "cantidad",2,
                                        "sucursalId",1
                                )
                        )
                );

        when(restTemplate.getForObject(
                "http://localhost:8085/api/v1/ventas/10",
                Map.class
        ))
        .thenReturn(venta);

        Pedido pedido =
                Pedido.builder()
                .usuarioId(1L)
                .ventaId(10L)
                .build();

        when(repository.save(
                org.mockito.ArgumentMatchers.any(Pedido.class)
        ))
        .thenReturn(pedido);

        Pedido resultado =
                service.crearPedido(dto);

        assertNotNull(resultado);

        assertEquals(
                10L,
                resultado.getVentaId()
        );
    }

    @Test
    void buscarPedido_deberiaEncontrarlo(){

        Pedido pedido =
                Pedido.builder()
                .ventaId(10L)
                .build();

        when(repository.findById(1L))
        .thenReturn(
                Optional.of(pedido)
        );

        Pedido resultado =
                service.buscarPorId(1L);

        assertEquals(
                10L,
                resultado.getVentaId()
        );
}

        @Test
        void listarPedidos_deberiaRetornarLista(){

        when(repository.findAll())
        .thenReturn(
                        List.of(
                        Pedido.builder()
                        .ventaId(1L)
                        .build()
                )
        );

        List<Pedido> resultado =
                service.listarPedidos();

        assertEquals(
                1,
                resultado.size()
        );
}

        @Test
        void historialUsuario_deberiaRetornarPedidos(){

                when(repository.findByUsuarioId(5L))
                .thenReturn(
                        List.of(
                                Pedido.builder()
                                .usuarioId(5L)
                                .ventaId(20L)
                                .build()
                )
        );

        List<Pedido> resultado =
                service.historialUsuario(5L);

        assertEquals(
                1,
                resultado.size()
        );
}

        @Test
        void actualizarEstado_deberiaCambiarEstado(){

                Pedido pedido =
                        Pedido.builder()
                        .ventaId(10L)
                        .estado(
                        EstadoPedido.EN_PREPARACION
                )
                .build();

        when(repository.findById(1L))
        .thenReturn(
                Optional.of(pedido)
        );

        when(repository.save(
                pedido
        ))
        .thenReturn(pedido);

        EstadoPedidoDTO dto =
                new EstadoPedidoDTO();

        dto.setEstado(
                EstadoPedido.EN_CAMINO
        );

        Pedido resultado =
                service.actualizarEstado(
                        1L,
                        dto
                );

        assertEquals(
                EstadoPedido.EN_CAMINO,
                resultado.getEstado()
        );
}

}

