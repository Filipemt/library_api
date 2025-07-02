package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.Client;
import com.filipecode.libraryApi.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    // todo: adicionar regras de negócios e validações
    // todo: crud de clientes
    public Client save(Client client) {
        var encodePassword = passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(encodePassword);
        return clientRepository.save(client);
    }

    public Client getByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
