package com.business.project.ms_estoque.controller.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record AtualizarProdutoResponse(Long id, String descricao, BigDecimal preco) {}
