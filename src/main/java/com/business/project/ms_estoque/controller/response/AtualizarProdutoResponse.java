package com.business.project.ms_estoque.controller.response;

import java.math.BigDecimal;

public record AtualizarProdutoResponse(Long id, String descricao, BigDecimal preco) {}
