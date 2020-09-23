package com.example.stavki.repos;

import com.example.stavki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByPasswordEquals(String password);
    public Optional<User> findByNameEquals(String name);
}
