package com.minsait.api.dto;

import lombok.Data;

@Data
public class ClienteResponse {

    private Long id;

    private String nome;

    private String endereco;

    private String email;

    private String telefone;
}
