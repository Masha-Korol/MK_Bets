package com.example.stavki.entities.controllers;

import java.util.List;

import com.example.stavki.entities.Client;
import com.example.stavki.entities.repository.ClientRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClientController {

    private final ClientRepository repository;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/clients")
    List<Client> all() {
        return repository.findAll();
    }

    @PostMapping("/clients")
    Client newClients(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    // Single item

    @GetMapping("/clients/{id}")
    Client one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        return repository.findById(id)
                .map(client -> {
                    client.setName(newClient.getName());
                    //client.setRole(newClient.getRole());
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
