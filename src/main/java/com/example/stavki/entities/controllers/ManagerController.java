package com.example.stavki.entities.controllers;

import java.util.List;

import com.example.stavki.entities.Manager;
import com.example.stavki.entities.repository.ManagerRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ManagerController {

    private final ManagerRepository repository;

    ManagerController(ManagerRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/managers")
    List<Manager> all() {
        return repository.findAll();
    }

    @PostMapping("/managers")
    Manager newManager(@RequestBody Manager newManager) {
        return repository.save(newManager);
    }

    // Single item

    @GetMapping("/manager/{id}")
    Manager one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(id));
    }

    @PutMapping("/managers/{id}")
    Manager replaceManager(@RequestBody Manager newManager, @PathVariable Long id) {

        return repository.findById(id)
                .map(manager -> {
                    manager.setName(newManager.getName());
                    //manager.setRole(newManager.getRole());
                    return repository.save(manager);
                })
                .orElseGet(() -> {
                    newManager.setId(id);
                    return repository.save(newManager);
                });
    }

    @DeleteMapping("/managers/{id}")
    void deleteManager(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

