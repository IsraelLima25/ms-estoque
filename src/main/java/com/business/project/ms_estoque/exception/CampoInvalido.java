package com.business.project.ms_estoque.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampoInvalido {

    private String campo;
    private String mensagem;

}
