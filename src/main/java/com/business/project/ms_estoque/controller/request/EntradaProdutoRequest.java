package com.business.project.ms_estoque.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EntradaProdutoRequest(
        @NotBlank String numeroNotaFiscalEntrada, @NotNull @Positive Integer quantidade) {}
