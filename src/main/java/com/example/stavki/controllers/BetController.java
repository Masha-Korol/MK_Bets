package com.example.stavki.controllers;

import java.util.List;

import com.example.stavki.model.Bet;
import com.example.stavki.model.Team;
import com.example.stavki.model.User;
import com.example.stavki.services.BetService;
import com.example.stavki.services.UserService;
import com.example.stavki.services.GameService;
import com.example.stavki.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BetController {

    @Autowired
    private BetService betService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

//_____Get Bets________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @GetMapping("/getBets")
    public String getAllBets(Model model) {
        List<Bet> betList = betService.getAll();
        model.addAttribute("bets", betList);
        return "getBets";
    }

//__Get All Available Bets___________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @GetMapping("/getAvailableBets")
    public String getAllBetsAvailable(Model model) {
        model.addAttribute("bets",betService.findAllAvailable());
        return "getBetsAvailable";
    }

//__Are You Sure_________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("areYouSureDeleteBet/{client_id}")
    public String areYouSure(@PathVariable("client_id") Integer id,
                             @RequestParam("name") String name, Model model)
    {
        User user = userService.findById(id).get();
        model.addAttribute("client", user);
        model.addAttribute("name",name);
        return "deleteBet";
    }

//__Delete Client's Bet_____________________________________________________________________________________

    @GetMapping("/deleteBet/{client_id}")
    public String deleteBetGet(@PathVariable("client_id") Integer client_id, Model model){
        User user = userService.findById(client_id).get();
        model.addAttribute("client", user);
        model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
        model.addAttribute("games", userService.getAllUserGames(user));
        model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
        return "client";
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/deleteBet/{client_id}/{name}")
    public String deleteBetById (@PathVariable("client_id") Integer id,
                                 @PathVariable("name") String name,
                                 @RequestParam("answer") String answer)
    {
        User user = userService.findById(id).get();

        if (answer.equals("yes"))
        {
           betService.deleteBet(user,name);
        }
        return "redirect:/deleteBet/{client_id}";
    }

//_Are You Sure________________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("areYouSureDeleteBets/{client_id}")
    public String areYouSureDeleteBets(@PathVariable("client_id") Integer id, Model model)
    {
        User user = userService.findById(id).get();
        model.addAttribute("client", user);
        return "deleteAllBets";
    }

//___Delete All Bets By Client Id______________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/deleteAllBets/{client_id}")
    public String deleteAllBetsById(@PathVariable("client_id") Integer id,
                                    @RequestParam("answer") String answer, Model model){

        User user = userService.findById(id).get();

        if (answer.equals("yes"))
        {
            betService.deleteUsersBets(user);
        }

        model.addAttribute("client", user);
        model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
        model.addAttribute("games", userService.getAllUserGames(user));
        model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
        return "client";
    }

//__Go to Making Bet_______________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/goToMakingBet/{client_id}")
    public String goToMakingBet(@PathVariable("client_id") Integer id,
                                @RequestParam("sport") String sport, Model model){

        User user = userService.getCurrentUser();
        List<Team> teamList = teamService.getAllBySport(sport);
        teamList.add(teamService.findByName("none").get());
        model.addAttribute("games",gameService.getAllAvailableGamesBySport(sport));
        model.addAttribute("client",user);
        model.addAttribute("teams",teamList);
        model.addAttribute("sport", sport);
        return "makeBet";
    }

//__Make a Bet_____________________________________________________________________________________________________

    @GetMapping("/makeBet/{client_id}")
    public String makeBetGet(@PathVariable("client_id") Integer client_id, Model model){
        User user = userService.findById(client_id).get();
        model.addAttribute("client", user);
        model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
        model.addAttribute("games", userService.getAllUserGames(user));
        model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
        return "client";
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/makeBet/{sport}/{client_id}")
    public String makeBet(@RequestParam("name") String name,
                          @RequestParam ("winner") String winner,
                          @RequestParam ("money") String money,
                          @PathVariable ("client_id") Integer clientId,
                          @PathVariable("sport") String sport, Model model) {

            User user = userService.findById(clientId).get();
            String answer;
            try{
                answer = betService.checkMakeBet(money,user,name,winner);
                if(answer.equals("moneyString")){
                    List<Team> teamList = teamService.getAllBySport(sport);
                    model.addAttribute("games",gameService.getAllAvailableGamesBySport(sport));
                    model.addAttribute("client",userService.findById(clientId).get());
                    model.addAttribute("teams",teamList);
                    model.addAttribute("sport", sport);
                    return "moneyString";
                }
            }
            catch (NumberFormatException e){
                List<Team> teamList = teamService.getAllBySport(sport);
                teamList.add(teamService.findByName("none").get());
                model.addAttribute("games",gameService.getAllAvailableGamesBySport(sport));
                model.addAttribute("client",userService.findById(clientId).get());
                model.addAttribute("teams",teamList);
                model.addAttribute("sport", sport);
                return "moneyString";
            }

            if(answer.equals("addMoneyError")) {
                model.addAttribute("client", user);
                return "addMoneyError";
            }

            if(answer.equals("moreThanOnce")){
                List<Team> teamList = teamService.getAllBySport(sport);
                teamList.add(teamService.findByName("none").get());
                    model.addAttribute("games",gameService.getAllAvailableGamesBySport(sport));
                    model.addAttribute("client",user);
                    model.addAttribute("teams",teamList);
                    return "moreThanOnce";
                }

            if(answer.equals("noGameTakePart")){
                List<Team> teamList = teamService.getAllBySport(sport);
                teamList.add(teamService.findByName("none").get());
                model.addAttribute("name", winner);
                model.addAttribute("games",gameService.getAllAvailableGamesBySport(sport));
                model.addAttribute("client",user);
                model.addAttribute("teams",teamList);
                        return "noGameTakePart";
                    }

            return "redirect:/makeBet/{client_id}";
    }
}