package com.example.stavki.controllers;

import com.example.stavki.model.*;
import com.example.stavki.repos.TeamRepository;
import com.example.stavki.services.UserService;
import com.example.stavki.services.GameService;
import com.example.stavki.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

//__Find Team by name______________________________________________________________________________________________

    @PostMapping("/findTeam")
    public String findTeam(@RequestParam("name") String name, Model model){

        if (!teamService.findTeamByNameEquals(name).isPresent()){
            model.addAttribute("name", name);
            return "ERROR_NoSuchTeam";
        }

        model.addAttribute("team", teamService.findByName(name).get());
        model.addAttribute("games", gameService.getAllGamesByTeam(teamService.findTeamByNameEquals(name).get()));
        return "foundTeam";
    }

//_Transmition to Add new Team__________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/transmitionToAddNewTeam/{manager_id}")
    public String transmitionToAddNewTeam(@PathVariable("manager_id") Integer manager_id, Model model) {
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "addNewTeam";
    }

//__Add New Team______________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/addNewTeam/{manager_id}")
    public String addNewTeam(@PathVariable("manager_id") Integer manager_id,
                             @RequestParam("name") String team_name,
                             @RequestParam("sport") String sport, Model model)
    {
        String answer = teamService.validate(team_name, sport);

        if(answer.equals("noneTeamName")){
            model.addAttribute("manager", userService.findById(manager_id).get());
            model.addAttribute("manager", userService.findById(manager_id).get());
            return "noneTeamName";
        }

        if (answer.equals("tokenTeamName")){
            model.addAttribute("name", team_name);
            model.addAttribute("manager", userService.findById(manager_id).get());
            model.addAttribute("manager", userService.findById(manager_id).get());
            return "tokenTeamName";
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//__Are You Sure Delete Team____________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/areYouSureDeleteTeam/{manager_id}")
    public String areYouSureDeleteTeam(@PathVariable("manager_id") Integer manager_id,
                                       @RequestParam("team_name") String team_name, Model model)
    {
        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("team", teamService.findTeamByNameEquals(team_name).get());
        return "deleteTeam";
    }

//___Delete Team By Manager__________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteTeam/{manager_id}/{team_id}")
    public String deleteTeam(@PathVariable("manager_id") Integer manager_id,
                             @PathVariable("team_id") Integer team_id,
                             @RequestParam("answer") String answer, Model model)
    {
        if (answer.equals("yes"))
        {
            String result = teamService.deleteTeam(team_id);
            if(result.equals("cantDeleteTeam")){
                model.addAttribute("manager", userService.findById(manager_id).get());
                return "cantDeleteTeam";
            }
        }

            model.addAttribute("manager", userService.findById(manager_id).get());
            model.addAttribute("clients",userService.getAllClients());
            model.addAttribute("teams", teamService.getAllExceptNone());
            model.addAttribute("avGames", gameService.getAllGamesAvailable());
            model.addAttribute("games",gameService.getAll());
            model.addAttribute("managers",userService.getAllManagers());
            return "manager";
    }

//_Transmition to Editing Team's name__________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/transmitionToEditTeam/{manager_id}")
    public String editTeamName(@PathVariable("manager_id") Integer manager_id,
                               @RequestParam("team_name") String team_name, Model model)
    {
        Team team = teamService.findTeamByNameEquals(team_name).get();
        model.addAttribute("team",team);
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "editTeam";
    }

//____Edit Team's name____________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("editTeam/{team_id}")
    public String editTeam(@PathVariable("team_id") Integer team_id,
                           @RequestParam("name") String name, Model model)
    {

        User user = userService.getCurrentUser();
        Team team = teamService.findById(team_id).get();
        String answer = teamService.editTeamName(name, team);
        if(answer.equals("usedTeamName")){
            model.addAttribute("name", name);
            model.addAttribute("team", team);
            model.addAttribute("manager", user);
            return "usedTeamName";
        }

        model.addAttribute("manager", user);
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }
}