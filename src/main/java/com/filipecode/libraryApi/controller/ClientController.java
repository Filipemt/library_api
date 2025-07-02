package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.model.entities.Client;
import com.filipecode.libraryApi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    // todo: criar DTO para transferência de dados, mapper
    public void save(@RequestBody Client client) {
        clientService.save(client);
    }
}
