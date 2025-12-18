package com.business.project.ms_estoque.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaidaProdutoReques(@NotBlank String numeroNotaFiscalSaida, @NotNull @Positive Integer quantidade) {}
