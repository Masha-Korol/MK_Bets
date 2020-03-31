package com.example.stavki.controllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.stavki.entities.Client;
import com.example.stavki.entities.Team;
import com.example.stavki.entities.Wager;
import com.example.stavki.repos.WagerRepository;
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
public class WagerController {

    private final WagerRepository repository;

    private WagerService wagerService;

    WagerController(WagerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/wagers")
    CollectionModel<EntityModel<Wager>> all() {

        List<EntityModel<Wager>> wagers = repository.findAll().stream()
                .map(wager -> new EntityModel<>(wager,
                        linkTo(methodOn(WagerController.class).one(wager.getId())).withSelfRel(),
                        linkTo(methodOn(WagerController.class).all()).withRel("wagers")))
                .collect(Collectors.toList());

        return new CollectionModel<>(wagers,
                linkTo(methodOn(WagerController.class).all()).withSelfRel());
    }

    //вместо предыдущего метода
    //@ApiOperation("Get the list of wagers")
    @GetMapping("/wagers")
    public ResponseEntity<List<Wager>> getListWager() {
        return ResponseEntity.ok(wagerService.getAll());
    }

    //@ApiOperation("Add new wager");
    @PostMapping("/wagers")
    ResponseEntity<Object> newWager(@RequestBody Wager newWager)
    {
        wagerService.addWager(newWager);
        return ResponseEntity.ok(newWager);
    }


    @GetMapping("/wagers/{id}")
    EntityModel<Wager> one(@PathVariable Long id) {

        Wager wager = repository.findById(id)
                .orElseThrow(() -> new WagerNotFoundException(id));

        return new EntityModel<>(wager,
                linkTo(methodOn(WagerController.class).one(id)).withSelfRel(),
                linkTo(methodOn(WagerController.class).all()).withRel("wagers"));
    }


    /*@PutMapping("/wagers/{id}")
    Wager replaceWager(@RequestBody Wager newWager, @PathVariable Long id) {

        return repository.findById(id)
                .map(wager -> {
                    wager.setClientFK(newWager.getClientFK());
                    wager.setFirstTeamWinning(newWager.isFirstTeamWinning());
                    wager.setGameFK(newWager.getGameFK());
                    wager.setMoney(newWager.getMoney());

                    return repository.save(wager);
                })
                .orElseGet(() -> {
                    newWager.setId(id);
                    return repository.save(newWager);
                });
    }*/

    //@ApiOperation("Delete wager");
    @DeleteMapping("/wagers/{id}")
    ResponseEntity deleteWager(@PathVariable Long id)
    {
        wagerService.delete(id);
        return ResponseEntity.ok().build();
    }
}