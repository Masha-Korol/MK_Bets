package com.example.stavki.services;

import java.util.ArrayList;
import java.util.List;
import com.example.stavki.model.*;
import com.example.stavki.repos.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private BetService betService;

    @Autowired
    private TeamService teamService;

    public void saveBet(Bet bet){
        betRepository.save(bet);
    }

    public List<Bet> getAll(){
        List<Bet> betList = betRepository.findAll();
        betList.sort(Bet.statusComparator);
        return betList;

    }

    public void deleteById(Integer id) throws NumberFormatException {
        betRepository.deleteById(id);
    }

    public void deleteBet(User user, String name){
        Bet thisBet=null;
        List<Bet> betList = user.getBetsList();
        for (Bet bet : betList)
        {
            if (bet.getGame().getName().equals(name))
            {
                thisBet = bet;
                break;
            }
        }
        deleteById(thisBet.getBetId());
        return;
    }

    public void deleteUsersBets(User user){

        List<Bet> betList = user.getBetsList();

        for (Bet bet : betList)
        {
            betService.deleteById(bet.getBetId());
        }
    }

    public List<Bet> findByGameIdEquals(Integer gameId){

        Game game=gameService.findById(gameId).get();
        List<Bet> betList=game.getBetList();
        return betList;
        }

    public String checkMakeBet(String newMoney, User user, String name, String winner)
            throws NumberFormatException{
        Double money = Double.parseDouble(newMoney);
        if(userService.getLeftMoney(user) < money){
            return "addMoneyError";
        }
        if(money<1){
            return "moneyString";
        }
        Game game = gameService.findGameByName(name).get();
        List<Game> gamesAvailableList = userService.getAllUsersGamesAvailable(user);
        for(Game game1 : gamesAvailableList){
        if(game1.getName().equals(game.getName())){
            return "moreThanOnce";
        }
        }
        if(!winner.equalsIgnoreCase(game.getTeam1().getName()) &&
                !winner.equalsIgnoreCase(game.getTeam2().getName()) &&
                !winner.equals("none")){
            return "noGameTakePart";
        }
        Bet bet = makeBet(winner,money,user, game);
        return "";
    }

    public Bet makeBet(String winner,double money,User user, Game game){

        Team teamWinner = teamService.findByName(winner).get();
        Bet bet = new Bet(teamWinner,money,user,game);
        betService.saveBet(bet);
        game.addBetToList(bet);

        return bet;
    }

    public List<Bet> findAllAvailable(){
     List<Game> gamesAvailable=gameService.getAllGamesAvailable();
     List<Bet> betList=new ArrayList<>();
     for(Game game : gamesAvailable)
     {
         betList.addAll(game.getBetList());
     }
     return betList;
    }
}
