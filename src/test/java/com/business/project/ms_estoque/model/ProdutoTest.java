package com.business.project.ms_estoque.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.business.project.ms_estoque.exception.BusinessException;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProdutoTest {

    @Test
    @DisplayName("Deve reduzir a quantidade do produto ao dar saída")
    void testDarSaida_ReduzQuantidade() {
        var produto = Instancio.create(Produto.class);
        produto.setQuantidade(10);
        produto.darSaida(3);
        assertThat(produto.getQuantidade()).isEqualTo(7);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar dar saída com quantidade maior que a disponível")
    void testDarSaida_ReduzQuantidade_MaiorQuePermitidoDeveLancarException() {
        var produto = Instancio.create(Produto.class);
        produto.setQuantidade(10);
        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            produto.darSaida(11);
                        });
        assertThat(exception.getMessage())
                .isEqualTo("Quantidade insuficiente em estoque para realizar a saída.");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar dar saída com quantidade zerada")
    void testDarSaida_ReduzQuantidade_ZeradaDeveLancarException() {
        var produto = Instancio.create(Produto.class);
        produto.setQuantidade(0);
        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            produto.darSaida(1);
                        });
        assertThat(exception.getMessage())
                .isEqualTo("Quantidade insuficiente em estoque para realizar a saída.");
    }

    @Test
    @DisplayName("Deve aumentar a quantidade do produto ao dar entrada")
    void tesDarEntrada_AumentaQuantidade() {
        var produto = Instancio.create(Produto.class);
        produto.setQuantidade(5);
        assertThat(produto.getQuantidade()).isEqualTo(5);
        produto.darEntrada(5);
        assertThat(produto.getQuantidade()).isEqualTo(10);
    }

    @Test
    @DisplayName("Não deve aumentar a quantidade do produto ao dar entrada com quantidade zerada")
    void tesDarEntrada_AumentaQuantidadeIgualZeroDeveLancarException() {
        var produto = Instancio.create(Produto.class);
        produto.setQuantidade(2);
        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            produto.darEntrada(0);
                        });
        assertThat(exception.getMessage())
                .isEqualTo("A quantidade de entrada deve ser maior que zero.");
    }
}
