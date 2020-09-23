package com.example.stavki.controllers;

import com.example.stavki.model.*;
import com.example.stavki.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ManagerController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BetService betService;

    @Autowired
    private UserService userService;

//__Get Managers____________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @GetMapping("/getManagers")
    public String getAllManagers(Model model) {
        model.addAttribute("managers", userService.getAllManagers());
        return "getManagers";
    }

//__Transmition to registration __________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @GetMapping("/registerNewManager/{manager_id}")
    public String registration(@PathVariable ("manager_id") Integer manager_id, Model model){
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "managerRegistration";}

//__Add New Manager__________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/addNewManager")
    public String addNewManager(@RequestParam("manager_name") String name,
                                @RequestParam("password") String password, Model model)
    {

        String answer = userService.validate(name, password,"MANAGER");

        if(answer.equals("ERROR_TokenManagerName")){
            model.addAttribute("name", name);
            return "ERROR_TokenManagerName";
        }
        if(answer.equals("ERROR_TokenPassword")){
            model.addAttribute("password", password);
            return "ERROR_TokenPassword";
        }

        User user = userService.addUser(name, password, "MANAGER");

        model.addAttribute("manager", user);
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//__Are You Sure Delete Manager___________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/areYouSureDeleteManager/{manager_id}")
    public String areYouSureDeleteManager (@PathVariable("manager_id") Integer manager_id,
                                           @RequestParam("managerToDelete_name") String managerToDelete_name,
                                           Model model)
    {
        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("managerToDelete", userService.findByName(managerToDelete_name).get());
        return "deleteManager";
    }

//___Delete Manager_______________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteManager/{manager_id}/{managerToDelete_id}")
    public String deleteManagerById(@PathVariable("manager_id") Integer manager_id,
                                                    @PathVariable("managerToDelete_id") Integer managerToDelete_id,
                                                    @RequestParam("answer") String answer,
                                                    Model model){

        if (answer.equals("yes"))
        {
            userService.delete(managerToDelete_id);
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
        }

//_Are You Sure Delete Manager Yourself____________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/areYouSureDeleteManagerYourself/{manager_id}")
    public String areYouSureDeleteManagerByYourself(@PathVariable("manager_id") Integer manager_id, Model model)
    {
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "areYouSureDeleteManager";
    }

//___Delete Manager_______________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteManager/{manager_id}")
    public String deleteManager(@PathVariable("manager_id") Integer manager_id,
                                @RequestParam("answer") String answer, Model model){

        if (answer.equals("yes"))
        {
            userService.delete(manager_id);
            model.addAttribute("avGames", gameService.getAllGamesAvailable());
            return "main";
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//___Transmition to making game __________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/makeGameTrans/{manager_id}")
    public String makeGameTrans(@PathVariable("manager_id") Integer manager_id,
                                @RequestParam("sport") String sport, Model model){
        model.addAttribute("teams", teamService.getAllBySport(sport));
        model.addAttribute("sport", sport);
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "makeGame";
    }

//__Make Game_________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/makeGame/{manager_id}/{sport}")
    public String makeGame(@PathVariable("manager_id") Integer manager_id,
                           @PathVariable("sport") String sport,
                           @RequestParam("team1_name") String team1Name,
                           @RequestParam("team2_name") String team2Name,
                           @RequestParam("name") String name,
                           @RequestParam("delay") long delay, Model model)
    {
            if (team1Name.equals(team2Name)){
                model.addAttribute("teams", teamService.getAllBySport(sport));
                model.addAttribute("sport", sport);
                model.addAttribute("manager", userService.findById(manager_id).get());
                return "betweenSameTeam";
            }
            if (gameService.findGameByName(name).isPresent()) {
                model.addAttribute("teams", teamService.getAllBySport(sport));
                model.addAttribute("sport", sport);
                model.addAttribute("name", name);
                model.addAttribute("manager", userService.findById(manager_id).get());
                return "gameNameToken";
            }

            Game game = userService.makeGame(team1Name, team2Name, name, model, delay);

            model.addAttribute("manager", userService.findById(manager_id).get());
            model.addAttribute("clients",userService.getAllClients());
            model.addAttribute("teams", teamService.getAllExceptNone());
            model.addAttribute("avGames", gameService.getAllGamesAvailable());
            model.addAttribute("games",gameService.getAll());
            model.addAttribute("managers",userService.getAllManagers());
            return "manager";
    }

//_____________________________________________________________________________________________

    @PostMapping("/goToClientError")
    public String open(Model model){
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        return "ERROR_NoSuchClient";
    }

//__Transmition to Manager's Editing_________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/transmitionToEditManager/{manager_id}")
    public String transmitionToEditManager(@PathVariable("manager_id") Integer manager_id,
                                          Model model)
    {
        User user = userService.findById(manager_id).get();
        model.addAttribute("manager",user);
        return "editManager";
    }

//____Edit Manager's name___________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("editManager/{manager_id}")
    public String editManager(@PathVariable("manager_id") Integer manager_id,
                              @RequestParam("name") String name, Model model)
    {
        User user = userService.findById(manager_id).get();
        String answer = userService.editUser(name, manager_id);

        if(answer.equals("tokenManagerName")){
            model.addAttribute("newName", name);
            model.addAttribute("manager",user);
            return "tokenManagerName";
        }

        model.addAttribute("manager", user);
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//_______________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/backToMainPage/{manager_id}")
    public String backToMainPage(@PathVariable("manager_id") Integer manager_id, Model model){
        User user = userService.findById(manager_id).get();
        model.addAttribute("manager", user);
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//__Exit To Main Page______________________________________________________________________________________________

    @GetMapping("/exitToMainPage")
    public String exitToMainPage(Model model){
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        return "main";
    }

//_____________________________________________________________________________________________

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model){

        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        if(error!=null){
            return "ERROR_NoSuchClient";
        }
        if(logout!=null){
            return "main";
        }
        return "";
    }
}