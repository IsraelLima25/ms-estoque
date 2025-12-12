package com.business.project.ms_estoque.controller;

import com.business.project.ms_estoque.controller.request.ProdutoRequest;
import com.business.project.ms_estoque.controller.response.ProdutoResponse;
import com.business.project.ms_estoque.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest produtoRequest,
                                                 UriComponentsBuilder uriBuilder) {

        var model = produtoRepository.save(produtoRequest.toModel());
        var response = new ProdutoResponse(
                model.getId(),
                model.getDescricao(),
                model.getPreco()
        );
        return ResponseEntity.created(uriBuilder.path("/produtos/{id}")
                .buildAndExpand(model.getId()).toUri()).body(response);
    }
}
