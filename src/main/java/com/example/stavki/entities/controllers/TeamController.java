package com.example.stavki.controllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.stavki.entities.Client;
import com.example.stavki.entities.Manager;
import com.example.stavki.entities.Team;
import com.example.stavki.repos.TeamRepository;
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
public class TeamController {

    private final TeamRepository repository;

    private TeamService teamService;

    TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/teams")
    CollectionModel<EntityModel<Team>> all() {

        List<EntityModel<Team>> teams = repository.findAll().stream()
                .map(team -> new EntityModel<>(team,
                        linkTo(methodOn(TeamController.class).one(team.getName())).withSelfRel(),
                        linkTo(methodOn(TeamController.class).all()).withRel("teams")))
                .collect(Collectors.toList());

        return new CollectionModel<>(teams,
                linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    //вместо предыдущего метода
    //@ApiOperation("Get the list of teams")
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getListTeam() {
        return ResponseEntity.ok(teamService.getAll());
    }

    //@ApiOperation("Add new team");
    @PostMapping("/teams")
    ResponseEntity<Object> newTeam(@RequestBody Team newTeam)
    {
        teamService.addTeam(newTeam);
        return ResponseEntity.ok(newTeam);
    }

    @GetMapping("/teams/{id}")
    EntityModel<Team> one(@PathVariable String id) {

        Team team = repository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return new EntityModel<>(team,
                linkTo(methodOn(TeamController.class).one(id)).withSelfRel(),
                linkTo(methodOn(TeamController.class).all()).withRel("teams"));
    }


    /*@PutMapping("/teams/{id}")
    Team replaceTeam(@RequestBody Team newTeam, @PathVariable String id) {

        return repository.findById(id)
                .map(team -> {
                    team.setHistoryOfVictories(newTeam.getHistoryOfVictories());
                    team.setWinningRate(newTeam.getWinningRate());
                    team.setName(newTeam.getName());


                    return repository.save(team);
                })
                .orElseGet(() -> {
                    newTeam.setName(id);
                    return repository.save(newTeam);
                });
    }*/

    //@ApiOperation("Delete team");
    @DeleteMapping("/teams/{id}")
    ResponseEntity deleteTeam(@PathVariable Long id)
    {
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }
}