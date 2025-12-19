package com.business.project.ms_estoque.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.business.project.ms_estoque.controller.enums.TipoMovimentacao;
import com.business.project.ms_estoque.controller.request.AtualizarProdutoRequest;
import com.business.project.ms_estoque.controller.request.CriarProdutoRequest;
import com.business.project.ms_estoque.controller.request.EntradaProdutoRequest;
import com.business.project.ms_estoque.controller.request.SaidaProdutoRequest;
import com.business.project.ms_estoque.exception.RestExceptionHandler;
import com.business.project.ms_estoque.model.Produto;
import com.business.project.ms_estoque.repository.ProdutoRepository;
import com.business.project.ms_estoque.service.HistoricoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {ProdutoController.class, RestExceptionHandler.class})
@AutoConfigureJsonTesters
class ProdutoControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean ProdutoRepository produtoRepository;

    @MockBean HistoricoService historicoService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setupInitialize() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve criar produto e retornar 201")
    void deveCriarProduto() throws Exception {

        var produtoModel = Instancio.create(Produto.class);

        when(produtoRepository.save(any())).thenReturn(produtoModel);

        var requestProduto = Instancio.create(CriarProdutoRequest.class);

        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Não deve criar produto e retornar 400 quando dados inválidos")
    void naoDeveCriarProduto() throws Exception {

        var requestProduto = Instancio.ofBlank(CriarProdutoRequest.class);

        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Quando produto não encontrado deve retornar 404 ao atualizar")
    void naoDeveAtualizarProdutoNaoEncontrado() throws Exception {

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        var requestProduto = Instancio.create(AtualizarProdutoRequest.class);
        mockMvc.perform(
                        put("/produtos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar produto e retornar 200")
    void deveAtualizarProduto() throws Exception {

        var produtoModel = Instancio.create(Produto.class);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));

        var requestProduto = Instancio.create(AtualizarProdutoRequest.class);

        mockMvc.perform(
                        put("/produtos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve listar todos os produtos e retornar 200")
    void deveListarTodosProdutos() throws Exception {
        when(produtoRepository.findAll())
                .thenReturn(Instancio.ofList(Produto.class).size(2).create());
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Quando produto não encontrado deve retornar 404 ao filtrar por ID")
    void naoDeveRetornarProdutoFiltradoNaoEncontrado() throws Exception {

        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/produtos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve filtrar produto por ID e retornar 200")
    void deveRetornarProdutoFiltradoEncontrado() throws Exception {

        var produtoModel = Instancio.create(Produto.class);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));

        mockMvc.perform(get("/produtos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Quando produto não encontrado deve retornar 404 ao dar entrada")
    void naoDeveDarEntradaProdutoNaoEncontrado() throws Exception {

        var entradaProdutoRequest = Instancio.create(EntradaProdutoRequest.class);
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(
                        post("/produtos/1/entrada")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(entradaProdutoRequest)))
                .andExpect(status().isNotFound());
        verify(historicoService, never()).registrar(any(Produto.class), any(), any(), any());
    }

    @Test
    @DisplayName("Deve dar entrada no produto e retornar 200")
    void deveDarEntradaProduto() throws Exception {

        var entradaProdutoRequest = Instancio.create(EntradaProdutoRequest.class);
        var produtoModel = Instancio.create(Produto.class);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));

        mockMvc.perform(
                        post("/produtos/1/entrada")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(entradaProdutoRequest)))
                .andExpect(status().isOk());
        verify(historicoService, times(1))
                .registrar(
                        produtoModel,
                        TipoMovimentacao.ENTRADA,
                        entradaProdutoRequest.numeroNotaFiscalEntrada(),
                        entradaProdutoRequest.quantidade());
    }

    @Test
    @DisplayName("Quando produto não encontrado deve retornar 404 ao dar saída")
    void naoDeveDarSaidaProdutoNaoEncontrado() throws Exception {

        var saidaProdutoRequest = Instancio.create(SaidaProdutoRequest.class);
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(
                        post("/produtos/1/saida")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(saidaProdutoRequest)))
                .andExpect(status().isNotFound());
        verify(historicoService, never()).registrar(any(Produto.class), any(), any(), any());
    }

    @Test
    @DisplayName("Deve dar saída no produto e retornar 200")
    void deveDarSaidaProduto() throws Exception {

        var produtoModel = Instancio.create(Produto.class);
        produtoModel.setQuantidade(10);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));
        var saidaProdutoRequest = new SaidaProdutoRequest("5050505050", 5);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));
        mockMvc.perform(
                        post("/produtos/1/saida")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(saidaProdutoRequest)))
                .andExpect(status().isOk());
        verify(historicoService, times(1))
                .registrar(
                        produtoModel,
                        TipoMovimentacao.SAIDA,
                        saidaProdutoRequest.numeroNotaFiscalSaida(),
                        saidaProdutoRequest.quantidade());
    }
}
