package com.minsait.api.controller;

import com.minsait.api.dto.ClienteRequest;
import com.minsait.api.dto.ClienteResponse;
import com.minsait.api.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

@Tag(name = "Práticas tecnológicas", description = "endpoints do curso de práticas tecnológicas")
public interface ApiSwagger {
    @Operation(summary = "Busca um cliente pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados do cliente retornados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    ResponseEntity<ClienteResponse> findById(@PathVariable Long id);


    @Operation(summary = "Busca todos os clientes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados os clientes"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros retornados com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    ResponseEntity<Page<ClienteResponse>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String endereco,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    );

    @Operation(summary = "Insere um novo cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente inserido com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest clienteRequest, HttpServletRequest request) throws URISyntaxException;

    @Operation(summary = "Atualiza um cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    ResponseEntity<ClienteResponse> update(@RequestBody ClienteRequest clienteRequest);

    @Operation(summary = "Exclui um cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados os clientes"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    ResponseEntity<MessageResponse> delete(@PathVariable Long id);

}
