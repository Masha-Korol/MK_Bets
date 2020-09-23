package com.example.stavki.services;

import java.util.*;
import com.example.stavki.model.Game;
import com.example.stavki.model.Status;
import com.example.stavki.model.Team;
import com.example.stavki.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamService teamService;

    public void saveGame(Game game){
        gameRepository.save(game);
    }

    public List<Game> getAll(){
        List<Game> gameList = gameRepository.findAll();
        gameList.sort(Game.statusComparator);
        return gameList;
    }

    public void delete(String name) {
        Game game = findGameByName(name).get();
        gameRepository.deleteById(game.getGameId());
    }

    public Optional<Game> findById(Integer id){return gameRepository.findById(id);}

    public Optional<Game> findGameByName(String name){
    return gameRepository.findGameByNameEquals(name);
    }

    public List<Game> getAllAvailableGamesBySport(String sport){
        List<Game> gameList = getAll();
        List<Game> gameList1 = new ArrayList<>();

        for (Game game : gameList)
        {
            Team team = teamService.findById(game.getTeam1().getTeam_id()).get();
            if (team.getSport().equals(sport) && game.getStatus().equals(Status.AVAILABLE))
            {
                gameList1.add(game);
            }
        }

        return gameList1;
    }

    public List<Game> getAllGamesByTeam(Team team){

        List<Game> gameList = getAll();
        List<Game> gameList1 = new ArrayList<>();

        for (Game game : gameList)
        {
            if (game.getTeam1().getName().equals(team.getName()) ||
                game.getTeam2().getName().equals(team.getName()))
            {
                gameList1.add(game);
            }
        }

        return gameList1;
    }

    public void deleteAll(){gameRepository.deleteAll();}

    public List<Game> getAllGamesAvailable(){
        List<Game> gameList=gameRepository.findAll();
        List<Game> gamesAvailable=new ArrayList<>();
        for(Game game : gameList){
            if (game.getStatus() == Status.AVAILABLE)
            {
                gamesAvailable.add(game);
            }
        }
        return gamesAvailable;}
}
