package com.example.stavki.repos;

import com.example.stavki.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {

    public void deleteAll();

    public Optional<Game> findGameByNameEquals(String name);
}

