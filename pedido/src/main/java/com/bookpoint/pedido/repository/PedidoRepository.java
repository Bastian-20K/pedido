package com.bookpoint.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookpoint.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);
}
