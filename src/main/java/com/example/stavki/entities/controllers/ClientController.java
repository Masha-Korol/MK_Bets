package com.example.stavki.controllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;


import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.stavki.entities.Client;
import com.example.stavki.repos.ClientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
// end::hateoas-imports[]

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//ResponseEntity extends HttpEntity
//Used to return value from controller method

@RestController
public class ClientController {

    private final ClientRepository repository;

    private ClientService clientService;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clients")
    CollectionModel<EntityModel<Client>> all() {

        List<EntityModel<Client>> clients = repository.findAll().stream()
                .map(client -> new EntityModel<>(client,
                        linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
                        linkTo(methodOn(ClientController.class).all()).withRel("clients")))
                .collect(Collectors.toList());

        return new CollectionModel<>(clients, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }

    //вместо предыдущего метода
    //@ApiOperation("Get the list of clients")
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getListClient() {
        return ResponseEntity.ok(clientService.getAll());
    }

    //ok - status
    //@ApiOperation("Add new client");
    @PostMapping("/clients")
    ResponseEntity<Object> newClient(@RequestBody Client newClient)
    {
        clientService.addClient(newClient);
        return ResponseEntity.ok(newClient);
    }

    //@ApiOperation("Get client");
    @GetMapping("/clients/{id}")
    EntityModel<Client> one(@PathVariable Long id) {

        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return new EntityModel<>(client,
                linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("clients"));
    }


    /*@PutMapping("/clients/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        return repository.findById(id)
                .map(client -> {
                    client.setCurrent(newClient.getCurrent());
                    client.setHistory(newClient.getHistory());
                    client.setMoney(newClient.getMoney());
                    client.setName(newClient.getName());

                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });
    }*/

    //@ApiOperation("Delete client");
    @DeleteMapping("/clients/{id}")
    ResponseEntity deleteClient(@PathVariable Long id)
    {
        clientService.delete(id);
        return ResponseEntity.ok().build();
    }
}