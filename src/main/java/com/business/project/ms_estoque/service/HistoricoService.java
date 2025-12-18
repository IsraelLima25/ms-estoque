package com.business.project.ms_estoque.service;

import com.business.project.ms_estoque.controller.enums.TipoMovimentacao;
import com.business.project.ms_estoque.model.Historico;
import com.business.project.ms_estoque.model.Produto;
import com.business.project.ms_estoque.repository.HistoricoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoricoService {

    private HistoricoRepository historicoRepository;

    public void registrar(
            Produto produto,
            TipoMovimentacao tipoMovimentacao,
            String numeroNotaFiscalEntrada,
            Integer quantidade) {
        var historico =
                new Historico(produto, tipoMovimentacao, numeroNotaFiscalEntrada, quantidade);
        historicoRepository.save(historico);
    }
}
