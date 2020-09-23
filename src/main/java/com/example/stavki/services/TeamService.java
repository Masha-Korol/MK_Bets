package com.example.stavki.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.stavki.model.Game;
import com.example.stavki.model.Team;
import com.example.stavki.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameService gameService;

    public void saveTeam(Team team){
        teamRepository.save(team);
    }

    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    public List<Team> getAllExceptNone(){
        List<Team> allTeams = getAll();
        List<Team> teams = new ArrayList<>();
        for (Team team : allTeams)
        {
            if (!team.getName().equals("none") && !team.getName().equals(""))
                teams.add(team);
        }

        return teams;
    }

    public String validate(String name, String sport){
        if(name.equals("none")){
            return "noneTeamName";
        }
        if(findTeamByNameEquals(name).isPresent()){
            return "tokenTeamName";
        }
        Team team=new Team(name, sport);
        saveTeam(team);
        return "";
    }

    public String deleteTeam(Integer team_id){
        boolean ifTeamCompetes = false;
        List<Game> games = gameService.getAll();
        for (Game game : games)
        {
            if (game.getTeam1().getTeam_id() == team_id || game.getTeam2().getTeam_id() == team_id)
            {
                ifTeamCompetes = true;
                break;
            }
        }

        if (ifTeamCompetes == true)
        {
            return "cantDeleteTeam";
        }

        delete(team_id);
        return "";
    }

    public String editTeamName(String name, Team team){
        if(findTeamByNameEquals(name).isPresent()){
            return "usedTeamName";
        }
        team.setName(name);
        saveTeam(team);
        return "";
    }

    public void delete(Integer id) {
        teamRepository.deleteById(id);
    }

    public Optional<Team> findById(Integer id){return teamRepository.findById(id);}

    public Optional<Team> findByName(String name){return teamRepository.findByNameEquals(name);}

    public List<Team> getAllBySport(String sport){
        List <Team> teams = teamRepository.getAllBySportEquals(sport);
        teams.remove(findByName("").get());
        return teams;}

    public void deleteAll(){teamRepository.deleteAll();}

    public Optional<Team> findTeamByNameEquals (String name){return teamRepository.findTeamByNameEquals(name);}
}
