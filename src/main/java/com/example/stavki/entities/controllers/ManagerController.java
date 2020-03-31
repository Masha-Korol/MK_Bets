package com.example.stavki.controllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.stavki.entities.Client;
import com.example.stavki.entities.Game;
import com.example.stavki.entities.Manager;
import com.example.stavki.repos.ManagerRepository;
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

@RestController
public class ManagerController {

    private final ManagerRepository repository;

    private ManagerService managerService;

    ManagerController(ManagerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/managers")
    CollectionModel<EntityModel<Manager>> all() {

        List<EntityModel<Manager>> managers = repository.findAll().stream()
                .map(manager -> new EntityModel<>(manager,
                        linkTo(methodOn(ManagerController.class).one(manager.getId())).withSelfRel(),
                        linkTo(methodOn(ManagerController.class).all()).withRel("managers")))
                .collect(Collectors.toList());

        return new CollectionModel<>(managers,
                linkTo(methodOn(ManagerController.class).all()).withSelfRel());
    }

    //вместо предыдущего метода
    //@ApiOperation("Get the list of managers")
    @GetMapping("/managers")
    public ResponseEntity<List<Manager>> getListManager() {
        return ResponseEntity.ok(managerService.getAll());
    }

    //@ApiOperation("Add new manager");
    @PostMapping("/managers")
    ResponseEntity<Object> newManager(@RequestBody Manager newManager)
    {
        managerService.addManager(newManager);
        return ResponseEntity.ok(newManager);
    }


    @GetMapping("/managers/{id}")
    EntityModel<Manager> one(@PathVariable Long id) {

        Manager manager = repository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(id));

        return new EntityModel<>(manager,
                linkTo(methodOn(ManagerController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ManagerController.class).all()).withRel("managers"));
    }


    /*@PutMapping("/managers/{id}")
    Manager replaceManager(@RequestBody Manager newManager, @PathVariable Long id) {

        return repository.findById(id)
                .map(manager -> {
                    manager.setMoney(newManager.getMoney());
                    manager.setName(newManager.getName());

                    return repository.save(manager);
                })
                .orElseGet(() -> {
                    newManager.setId(id);
                    return repository.save(newManager);
                });
    }*/

    //@ApiOperation("Delete manager");
    @DeleteMapping("/managers/{id}")
    ResponseEntity deleteManager(@PathVariable Long id)
    {
        managerService.delete(id);
        return ResponseEntity.ok().build();
    }
}