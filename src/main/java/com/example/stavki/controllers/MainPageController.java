package com.example.stavki.controllers;

import com.example.stavki.services.UserService;
import com.example.stavki.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    //@Autowired
    //private UserService userService;

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("avGames", gameService.getAllGamesAvailable());
        return "main";
    }

}




























/*import com.example.stavki.repos.ClientRepository;
import com.example.stavki.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        //model.addAttribute("clients", clientService.getAll());
        return "main";
    }
}
*/