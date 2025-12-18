package com.business.project.ms_estoque.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AtualizarProdutoRequest(
        @NotBlank String descricao, @NotNull @Positive BigDecimal preco) {}
