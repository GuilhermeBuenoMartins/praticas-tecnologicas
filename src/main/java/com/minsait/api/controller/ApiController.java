package com.minsait.api.controller;

import com.minsait.api.dto.ClienteRequest;
import com.minsait.api.dto.ClienteResponse;
import com.minsait.api.dto.MessageResponse;
import com.minsait.api.repository.ClienteEntity;
import com.minsait.api.repository.ClienteRepository;
import com.minsait.api.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ApiController implements ApiSwagger {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/cliente")
    public ResponseEntity<Page<ClienteResponse>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String endereco,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        final var clienteEntity = new ClienteEntity();
        clienteEntity.setNome(nome);
        clienteEntity.setEndereco(endereco);
        Pageable pageable = PageRequest.of(page, pageSize);
        final var clienteEntityList = clienteRepository.findAll(clienteEntity.clienteEntitySpecification(), pageable);
        final var clienteResponsePage = ObjectMapperUtil.mapAll(clienteEntityList, ClienteResponse.class);
        return ResponseEntity.ok(clienteResponsePage);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable Long id) {
        final var clienteEntity = clienteRepository.findById(id);
        if (clienteEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final var clienteResponse = ObjectMapperUtil.map(clienteEntity.get(), ClienteResponse.class);
        return ResponseEntity.ok(clienteResponse);
    }

    @PostMapping("/cliente")
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest clienteRequest, HttpServletRequest request) throws URISyntaxException {
        final var clienteEntity = ObjectMapperUtil.map(clienteRequest, ClienteEntity.class);
        final var clienteInserted = clienteRepository.save(clienteEntity);
        final var clienteResponse = ObjectMapperUtil.map(clienteInserted, ClienteResponse.class);
        return ResponseEntity.created(new URI(request.getRequestURI().concat(clienteResponse.getId().toString()))).build();
    }

    @PutMapping("/cliente")
    public ResponseEntity<ClienteResponse> update(@RequestBody ClienteRequest clienteRequest) {
        final var clienteEntity = ObjectMapperUtil.map(clienteRequest, ClienteEntity.class);
        final var clienteUpdated = clienteRepository.save(clienteEntity);
        final var clienteResponse = ObjectMapperUtil.map(clienteUpdated, ClienteResponse.class);
        return ResponseEntity.ok(clienteResponse);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        final var clienteEntity = clienteRepository.findById(id);
        if (clienteEntity.isEmpty()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Cliente não encontrado")
                    .date(LocalDateTime.now())
                    .error(false)
                    .build(), HttpStatus.NOT_FOUND);
        }
        clienteRepository.delete(clienteEntity.get());
        return new ResponseEntity<>(MessageResponse.builder()
                .message("OK")
                .date(LocalDateTime.now())
                .error(false)
                .build(), HttpStatus.OK);
    }

}
