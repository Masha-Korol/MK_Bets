package com.example.stavki.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import com.example.stavki.model.Game;
import com.example.stavki.repos.GameRepository;
import com.example.stavki.services.UserService;
import com.example.stavki.services.GameService;
import com.example.stavki.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

//___Get Games_________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @GetMapping("/getGames")
    public String getAllGames(Model model) {
        List<Game> gameList = gameService.getAll();
        model.addAttribute("games", gameList);
        return "getGames";
    }

//______Find Game By Id_______________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/findGame")
    public String findGameById(@RequestParam("name") String name, Model model)
    {
        try {

            Game game=gameService.findGameByName(name).get();
            model.addAttribute("game", game);
            return "gameInfo";

        } catch (NoSuchElementException e){
            model.addAttribute("name", name);
            return "ERROR_NoSuchGame";
        }
    }

//__Are You Sure Delete Game_________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/areYouSureDeleteGame/{manager_id}")
    public String areYouSureDeleteGame(@PathVariable("manager_id") Integer manager_id,
                                       @RequestParam("name") String name, Model model)
    {
        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("name", name);
        return "deleteGame";
    }

//____Delete game______________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteGame/{manager_id}/{name}")
    public String deleteGame(@PathVariable("manager_id") Integer manager_id,
                             @PathVariable("name") String name,
                             @RequestParam("answer") String answer, Model model)
    {
        if (answer.equals("yes"))
        {
            gameService.delete(name);
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//___Are You Sure Delete All Games_____________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/areYouSureDeleteAllGames/{manager_id}")
    public String areYouSureDeleteAllGames(@PathVariable("manager_id") Integer manager_id, Model model)
    {
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "deleteAllGames";
    }

//__Delete All Games__________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteAllGames/{manager_id}")
    public String deleteAllGames(@PathVariable("manager_id") Integer manager_id,
                                 @RequestParam("answer") String answer, Model model){

        if (answer.equals("yes"))
        {
            gameService.deleteAll();
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }
}