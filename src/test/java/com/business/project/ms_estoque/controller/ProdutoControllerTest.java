package com.business.project.ms_estoque.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.business.project.ms_estoque.controller.request.ProdutoRequest;
import com.business.project.ms_estoque.exception.RestExceptionHandler;
import com.business.project.ms_estoque.model.Produto;
import com.business.project.ms_estoque.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {ProdutoController.class, RestExceptionHandler.class})
@AutoConfigureJsonTesters
class ProdutoControllerTest {

    @Autowired MockMvc mockMvc;

    @MockitoBean ProdutoRepository produtoRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setupInitialize() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve criar produto e retornar 201")
    void deveCriarProduto() throws Exception {

        var produtoModel = Instancio.create(Produto.class);

        when(produtoRepository.save(any())).thenReturn(produtoModel);

        var requestProduto = Instancio.create(ProdutoRequest.class);

        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Não deve criar produto e retornar 400 quando dados inválidos")
    void naoDeveCriarProduto() throws Exception {

        var requestProduto = Instancio.ofBlank(ProdutoRequest.class);

        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestProduto)))
                .andExpect(status().isBadRequest());
    }
}
