package com.business.project.ms_estoque.model;

import com.business.project.ms_estoque.controller.enums.TipoMovimentacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "tbl_historico")
@Getter
@NoArgsConstructor
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacao tipo;

    @Column(name = "numero_nota_fiscal")
    private String numeroNotaFiscal;

    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "quantidade")
    private Integer quantidade;

    public Historico(
            Produto produto, TipoMovimentacao tipo, String numeroNotaFiscal, Integer quantidade) {
        this.produto = produto;
        this.tipo = tipo;
        this.data = LocalDateTime.now();
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.quantidade = quantidade;
    }
}
