package com.example.stavki.entities.repository;

import com.example.stavki.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
