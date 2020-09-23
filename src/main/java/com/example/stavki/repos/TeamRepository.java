package com.example.stavki.repos;

import com.example.stavki.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    public void deleteAll();

    public Optional<Team> findTeamByNameEquals(String name);

    public Optional<Team> findByNameEquals(String name);

    public List<Team> getAllBySportEquals(String sport);

}
