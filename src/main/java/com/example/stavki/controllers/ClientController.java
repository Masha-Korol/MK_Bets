package com.example.stavki.controllers;

import java.util.NoSuchElementException;
import com.example.stavki.model.*;
import com.example.stavki.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

//__Get Clients_____________________________________________________________________________________

    //atribute - то, что пишешь в ftl <[atribute] as client>
    //в нашем случае это clients

    @Secured("ROLE_MANAGER")
    @GetMapping("/getClients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        return "getClients";
    }

//______Find Client By Id________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/findClientByName/{manager_id}")
    public String findClientById(@PathVariable("manager_id") Integer id,
                                 @RequestParam("name") String name, Model model)
    {
        try{
            User user=userService.findByName(name).get();
            model.addAttribute("client", user);
            return "foundClient";

        }
        catch (NoSuchElementException e){
            model.addAttribute("manager", userService.findById(id).get());
            model.addAttribute("name", name);
            return "ERROR_NoSuchElement";
        }
    }

//__Add New Client___________________________________________________________________________

    @PostMapping("/addNewClient")
    public String addNewClient(@RequestParam("client_name") String client_name,
                               @RequestParam("password") String password, Model model)
    {
        String answer = userService.validate(client_name,password,"CLIENT");

        if(answer.equals("ERROR_TokenClientName")){
            model.addAttribute("name", client_name);
            return "ERROR_TokenClientName";
        }
        if(answer.equals("ERROR_TokenClientPassword")){
            model.addAttribute("password", password);
            return "ERROR_TokenClientPassword";
        }

        User user = userService.addUser(client_name,password,"CLIENT");

        model.addAttribute("client", userService.findById(user.getId()).get());
        model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
        model.addAttribute("games", userService.getAllUserGames(user));
        model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
        return "client";
    }

//__Are You Sure_________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("areYouSure/{client_id}")
    public String areYouSure(@PathVariable("client_id") Integer client_id, Model model)
    {
        User user = userService.findById(client_id).get();
        model.addAttribute("client", user);
        return "areYouSure";
    }

//_Delete Client___________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/deleteClient/{client_id}")
    public String deleteClientById(@PathVariable("client_id") Integer client_id,
                                   @RequestParam("answer") String answer, Model model){

        if (answer.equals("yes"))
        {
            userService.delete(client_id);
        }

        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        return "main";
    }

//__Are you sure delete client_________________________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("areYouSureDeleteClient/{manager_id}")
    public String areYouSureDeleteClient(@PathVariable("manager_id") Integer manager_id,
                                         @RequestParam("name") String name, Model model){

        model.addAttribute("client", userService.findByName(name).get());
        model.addAttribute("manager", userService.findById(manager_id).get());
        return "deleteClient";
    }

//___Delete Client By Manager_____________________________________________________________________________________

    @Secured("ROLE_MANAGER")
    @PostMapping("/deleteClientByManager/{client_id}/{manager_id}")
    public String deleteClientByManager(@PathVariable("client_id") Integer client_id,
                                        @PathVariable("manager_id") Integer manager_id,
                                        @RequestParam("answer") String answer, Model model){

        if (answer.equals("yes"))
        {
            userService.delete(client_id);
        }

        model.addAttribute("manager", userService.findById(manager_id).get());
        model.addAttribute("clients",userService.getAllClients());
        model.addAttribute("teams", teamService.getAllExceptNone());
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("games",gameService.getAll());
        model.addAttribute("managers",userService.getAllManagers());
        return "manager";
    }

//__Transmition to adding money________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("addMoneyTrans/{client_id}")
    public String addMoneyTrans(@PathVariable("client_id") Integer client_id, Model model){
        model.addAttribute("client", userService.findById(client_id).get());
        return "addMoney";
    }

//____Add Money______________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/addMoneyToClient/{client_id}")
    public String addMoney(@PathVariable("client_id") Integer client_id,
                           @RequestParam("money") String newMoney, Model model)
    {
        Double money;
        try{
            money = Double.parseDouble(newMoney);
            if(money<=0){
                model.addAttribute("client", userService.findById(client_id).get());
                return "addMoneyString";
            }
            userService.addMoney(userService.findById(client_id).get(),money);
        }
        catch (NumberFormatException e){
            model.addAttribute("client", userService.findById(client_id).get());
            return "addMoneyString";
        }

            model.addAttribute("client", userService.findById(client_id).get());
            return "moneyAdded";
    }


//__Transmition to getting money________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("getMoneyTrans/{client_id}")
    public String getMoneyTrans(@PathVariable("client_id") Integer client_id, Model model){
        model.addAttribute("client", userService.findById(client_id).get());
        return "getMoney";
    }

//____Get Money From Client_____________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/getMoneyFromClient/{client_id}")
    public String getMoney(@PathVariable("client_id") Integer client_id,
                           @RequestParam("money") String newMoney, Model model)
    {
            Double money;
            String answer;
            User user = userService.findById(client_id).get();
            try{
                money = Double.parseDouble(newMoney);
                answer = userService.getMoney(user,money);
            }
            catch (NumberFormatException e){
                model.addAttribute("client", userService.findById(client_id).get());
                return "getMoneyString";
            }

            if(answer.equals("notEnoughMoney")){
                model.addAttribute("client", user);
                return "notEnoughMoney";
            }

            model.addAttribute("client", user);
            return "moneyGotten";
    }

//__Register New Client____________________________________________________________________________________

    @PostMapping("/registerNewClient")
    public String registerNewClient(){return "registration";}

//__Transmition to Client's Editing_________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/transmitionToEditClient/{client_id}")
    public String transmitionToEditClient(@PathVariable("client_id") Integer client_id,
                                          Model model)
    {
        User user = userService.findById(client_id).get();
        model.addAttribute("client",user);
        return "editClient";
    }

//____Edit Client's name____________________________________________________________________________________

    @GetMapping("/editClient/{client_id}")
    public String editClientGet(@PathVariable("client_id") Integer client_id, Model model){
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
    @PostMapping("editClient/{client_id}")
    public String editClient(@PathVariable("client_id") Integer client_id,
                             @RequestParam("name") String name, Model model)
    {

        String answer = userService.editUser(name, client_id);

        User user = userService.getCurrentUser();
        if(answer.equals("tokenManagerName")){
            model.addAttribute("name", name);
            model.addAttribute("client", userService.findById(client_id).get());
            return "tokenClientName";
        }
        return "redirect:/editClient/{client_id}";
    }

//___Back to main page____________________________________________________________________________________________________

    @PostMapping("/backToMainGage/{client_id}")
    public String backToMainPage(@PathVariable("client_id") Integer client_id, Model model){
        User user = userService.findById(client_id).get();
        model.addAttribute("client", user);
        model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
        model.addAttribute("games", userService.getAllUserGames(user));
        model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
        return "client";
    }

//__get Statistics________________________________________________________________________________________________________

    @Secured("ROLE_CLIENT")
    @PostMapping("/getStatistics/{client_id}")
    public String getStatistics(@PathVariable("client_id") Integer client_id, Model model){
        model.addAttribute("client", userService.findById(client_id).get());
        return "statistics";
    }

//___________________________________________________________________

    @GetMapping("/clientPage")
    public String clientPage (Model model){
            User user = userService.getCurrentUser();

            if(user.getRole().equals("CLIENT")){
            model.addAttribute("client", user);
            model.addAttribute("bets", userService.getAllUsersBetsAvailable(user));
            model.addAttribute("games", userService.getAllUserGames(user));
            model.addAttribute("finishedBets", userService.getAllUsersBetsFinished(user));
            model.addAttribute("avGames", gameService.getAllGamesAvailable());
            model.addAttribute("betsFinished", userService.getAllUsersBetsFinished(user));
            return "client";
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
