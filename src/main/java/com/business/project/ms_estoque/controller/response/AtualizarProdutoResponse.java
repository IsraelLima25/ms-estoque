package com.business.project.ms_estoque.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AtualizarProdutoResponse(Long id, String descricao, BigDecimal preco) {}
