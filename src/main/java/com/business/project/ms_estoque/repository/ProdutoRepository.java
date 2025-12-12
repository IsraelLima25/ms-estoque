package com.business.project.ms_estoque.repository;

import com.business.project.ms_estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> { }
