package com.bookpoint.pedido.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookpoint.pedido.model.EstadoPedido;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.service.PedidoService;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService service;

    @Test
    void listarPedidos_deberiaRetornar200()
            throws Exception {

        when(service.listarPedidos())
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/pedidos")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void buscarPedidoPorId_deberiaRetornar200()
            throws Exception {

        Pedido pedido =
                Pedido.builder()
                        .ventaId(1L)
                        .build();

        when(service.buscarPorId(1L))
                .thenReturn(pedido);

        mockMvc.perform(
                get("/api/v1/pedidos/1")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void historialUsuario_deberiaRetornar200()
            throws Exception {

        when(service.historialUsuario(1L))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/pedidos/usuario/1")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void actualizarEstado_deberiaRetornar200()
            throws Exception {

        Pedido pedido =
                Pedido.builder()
                        .estado(
                                EstadoPedido.EN_CAMINO
                        )
                        .build();

        when(service.actualizarEstado(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()
        ))
        .thenReturn(pedido);

        String json = """
                {
                "estado":"EN_CAMINO"
                }
                """;

        mockMvc.perform(
                put("/api/v1/pedidos/1/estado")
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(json)
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void crearPedido_deberiaRetornar200()
            throws Exception {

    Pedido pedido =
            Pedido.builder()
                    .usuarioId(1L)
                    .ventaId(10L)
                    .direccion("Av Siempre Viva 123")
                    .build();

    when(service.crearPedido(
            org.mockito.ArgumentMatchers.any()
    ))
    .thenReturn(pedido);

    String json = """
            {
                "usuarioId":1,
                "ventaId":10,
                "direccion":"Av Siempre Viva 123"
            }
            """;

    mockMvc.perform(
            post("/api/v1/pedidos")
                    .contentType(
                            MediaType.APPLICATION_JSON
                    )
                    .content(json)
    )
    .andExpect(
            status().isOk()
    );
}
}

