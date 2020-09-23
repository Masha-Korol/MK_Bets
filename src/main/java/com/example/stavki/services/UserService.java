package com.example.stavki.services;

import java.util.*;
import com.example.stavki.model.*;
import com.example.stavki.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetService betService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    public String validate(String name, String password, String role){

        if(role.equals("CLIENT")){
            if(findByName(name).isPresent()){
                return "ERROR_TokenClientName";
            }

            if(findByPassword(password).isPresent()){
                return "ERROR_TokenClientPassword";
            }
        }

        if(role.equals("MANAGER")){
            if(findByName(name).isPresent()){
                return "ERROR_TokenManagerName";
            }
            if(findByPassword(password).isPresent()){
                return "ERROR_TokenPassword";
            }
        }

        return "";
    }

    public String editUser(String name, Integer id){
        if(findByName(name).isPresent()){
            return "tokenManagerName";
        }
        User user = findById(id).get();
        user.setName(name);
        saveUser(user);
        return "";
    }

    public User addUser(String name, String password, String role){
        User user = new User(name,password,role);
        user = setPassword(user);
        saveUser(user);
        return user;
    }

    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllClients(){
        List<User> userList = userRepository.findAll();
        List<User> clientList = new ArrayList<>();

        for(User user : userList){
            if(user.getRole().equals("CLIENT")){
                clientList.add(user);
            }
        }
        return clientList;
    }

    public List<User> getAllManagers(){
        List<User> userList = userRepository.findAll();
        List<User> managerList = new ArrayList<>();

        for(User user : userList){
            if(user.getRole().equals("MANAGER")){
                managerList.add(user);
            }
        }
        return managerList;
    }

    public void addMoney(User user,Double money) {
        user.setMoney(user.getMoney()+money);
        saveUser(user);
    }

    public String getMoney(User user,double money)
    {
        if(user.getMoney()<money){
            return "notEnoughMoney";
        }
        user.setMoney(user.getMoney()-money);
        saveUser(user);
        return "";
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    public Optional<User> findByPassword(String password){
        return userRepository.findByPasswordEquals(password);
    }

    public Optional<User> findById(Integer id){ return userRepository.findById(id); }

    public Optional<User> findByName(String name){return userRepository.findByNameEquals(name);}

    public Game makeGame(String team1name, String team2name, String gameName, Model model, long delay) {
        Team team1=teamService.findTeamByNameEquals(team1name).get();
        Team team2=teamService.findTeamByNameEquals(team2name).get();
        Game game=new Game(team1,team2,gameName, teamService.findTeamByNameEquals("").get());
        gameService.saveGame(game);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                finishGame(game.getName());
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, delay*1000*60);

        return game;
    }

    public void finishGame(String game_name){

        Game game=gameService.findGameByName(game_name).get();

        game.setStatus(Status.FINISHED);
        gameService.saveGame(game);

        List<Team> participants=new ArrayList<>();
        participants.add(teamService.findById(game.getTeam1().getTeam_id()).get());
        participants.add(teamService.findById(game.getTeam2().getTeam_id()).get());
        Team team_none = teamService.findTeamByNameEquals("none").get();
        participants.add(team_none);
        Random random=new Random();
        Team winner = participants.get(random.nextInt(participants.size()));
        List<Bet> betList=betService.findByGameIdEquals(game.getGameId());

        for(Bet bet  : betList){

            User user = bet.getUser();

            if(bet.getWinningTeam().getTeam_id() != winner.getTeam_id())
            {
                getMoney(user, bet.getMoney());
                user.setLosses(user.getLosses() + 1);
            }
            else
            {
                addMoney(user, bet.getMoney());
                user.setWins(user.getWins() + 1);
            }
            saveUser(user);
        }

        game.setWinner(winner);
        gameService.saveGame(game);
    }

    public List<Bet> getAllUsersBetsFinished (User user){

        List<Bet> betList = user.getBetsList();
        List<Bet> betsFinishedList = new ArrayList<>();

        if(betList!=null){
            for(Bet bet : betList){
                if(bet.getGame().getStatus() == Status.FINISHED){
                    betsFinishedList.add(bet);
                }
            }
        }

        return betsFinishedList;
    }

    public List<Game> getAllUserGames(User user){

        List<Bet> betList = user.getBetsList();
        List<Game> gameListUsers = new ArrayList<>();

        if(betList!=null){
            for (Bet bet : betList)
            {
                gameListUsers.add(bet.getGame());
            }
        }

        return gameListUsers;
    }

    public List<Bet> getAllUsersBetsAvailable (User user){

        List<Bet> betList = user.getBetsList();
        List<Bet> betsAvailableList = new ArrayList<>();

        if(betList!=null){
            for (Bet bet : betList)
            {
                if (bet.getGame().getStatus() == Status.AVAILABLE)
                    betsAvailableList.add(bet);
            }
        }

        return betsAvailableList;
    }

    public List<Game> getAllUsersGamesAvailable(User user){
        List<Bet> betList = user.getBetsList();
        List<Game> gamesAvailableList = new ArrayList<>();

        for (Bet bet : betList)
        {
            if (bet.getGame().getStatus() == Status.AVAILABLE){
                gamesAvailableList.add(bet.getGame());
            }
        }

        return gamesAvailableList;
    }

    public Double getLeftMoney(User user){

        List<Bet> betsAvailableList = getAllUsersBetsAvailable(user);
        Double sumMoney=0.0;

        for(Bet bet : betsAvailableList)
        {
            sumMoney += bet.getMoney();
        }

        return user.getMoney() - sumMoney;
    }

//_______________________________________________________________

    public User getUser(String name){
        User user = userRepository.findByNameEquals(name).get();
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUser(s);

        if(user == null){
            throw new UsernameNotFoundException("There's no user with name = "+s);
        }
        return user;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User setPassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
