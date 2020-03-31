package com.example.stavki.entities.repository;

import com.example.stavki.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClientRepository extends JpaRepository<Client, Long> {
}
