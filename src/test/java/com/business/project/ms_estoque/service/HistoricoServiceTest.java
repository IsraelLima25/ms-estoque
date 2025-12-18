package com.business.project.ms_estoque.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.business.project.ms_estoque.controller.enums.TipoMovimentacao;
import com.business.project.ms_estoque.model.Historico;
import com.business.project.ms_estoque.model.Produto;
import com.business.project.ms_estoque.repository.HistoricoRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HistoricoServiceTest {

    @InjectMocks private HistoricoService historicoService;

    @Mock private HistoricoRepository historicoRepository;

    @Test
    @DisplayName("Deve registrar historico corretamente")
    void deveRegistrarHistorico() {
        var produto = Instancio.create(Produto.class);
        historicoService.registrar(produto, TipoMovimentacao.ENTRADA, "NF12345", 10);
        verify(historicoRepository, times(1)).save(any(Historico.class));
    }
}
