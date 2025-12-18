package com.business.project.ms_estoque.controller.request;

import com.business.project.ms_estoque.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CriarProdutoRequest(@NotBlank String descricao, @NotNull @Positive BigDecimal preco) {
    public Produto toModel() {
        return new Produto(descricao, preco);
    }
}
