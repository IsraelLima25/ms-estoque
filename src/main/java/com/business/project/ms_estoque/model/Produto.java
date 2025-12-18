package com.business.project.ms_estoque.model;

import com.business.project.ms_estoque.exception.BusinessException;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "tbl_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "quantidade")
    private Integer quantidade;

    public Produto(String descricao, BigDecimal preco) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = 0;
    }

    public Produto(String descricao, BigDecimal preco, Integer quantidade) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public void darSaida(Integer quantidadePedida) {
        if (this.quantidade < quantidadePedida || this.quantidade == 0) {
            throw new BusinessException(
                    "Quantidade insuficiente em estoque para realizar a saída.");
        }
        this.quantidade -= quantidadePedida;
    }

    public void darEntrada(Integer quantidadeEntrada) {
        if (quantidadeEntrada == 0) {
            throw new BusinessException("A quantidade de entrada deve ser maior que zero.");
        }
        this.quantidade += quantidadeEntrada;
    }
}
