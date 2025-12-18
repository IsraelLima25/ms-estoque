package com.business.project.ms_estoque.controller;

import com.business.project.ms_estoque.controller.request.AtualizarProdutoRequest;
import com.business.project.ms_estoque.controller.request.CriarProdutoRequest;
import com.business.project.ms_estoque.controller.response.AtualizarProdutoResponse;
import com.business.project.ms_estoque.controller.response.CriarProdutoResponse;
import com.business.project.ms_estoque.controller.response.ProdutoResponse;
import com.business.project.ms_estoque.model.Produto;
import com.business.project.ms_estoque.repository.ProdutoRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CriarProdutoResponse> criar(
            @Valid @RequestBody CriarProdutoRequest request, UriComponentsBuilder uriBuilder) {

        var model = produtoRepository.save(request.toModel());
        var response =
                new CriarProdutoResponse(model.getId(), model.getDescricao(), model.getPreco());
        return ResponseEntity.created(
                        uriBuilder.path("/produtos/{id}").buildAndExpand(model.getId()).toUri())
                .body(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AtualizarProdutoResponse> atualizar(
            @PathVariable("id") Long id, @Valid @RequestBody AtualizarProdutoRequest request) {

        Optional<Produto> possivelProduto = produtoRepository.findById(id);
        if (possivelProduto.isPresent()) {
            Produto produto = possivelProduto.get();
            produto.setDescricao(request.descricao());
            produto.setPreco(request.preco());
            var response =
                    new AtualizarProdutoResponse(
                            produto.getId(), produto.getDescricao(), produto.getPreco());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProdutoResponse>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> response =
                produtos.stream()
                        .map(
                                produto ->
                                        new ProdutoResponse(
                                                produto.getId(),
                                                produto.getDescricao(),
                                                produto.getPreco()))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
