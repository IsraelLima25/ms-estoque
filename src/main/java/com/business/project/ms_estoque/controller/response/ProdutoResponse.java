package com.business.project.ms_estoque.controller.response;

import java.math.BigDecimal;

public record ProdutoResponse(Long id, String descricao, BigDecimal preco, Integer quantidade) {}
