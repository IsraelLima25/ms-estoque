package com.business.project.ms_estoque.repository;

import com.business.project.ms_estoque.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {}
