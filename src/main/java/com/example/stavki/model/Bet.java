package com.example.stavki.model;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import java.util.Comparator;

@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Integer betId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team winningTeam;

    @Column(name = "money")
    private double money;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

//__Methods_________________________________________________________________________

    public static Comparator<Bet> statusComparator = new Comparator<Bet>() {
        @Override
        public int compare(Bet o1, Bet o2) {
            return o1.getGame().getStatus().compareTo(o2.getGame().getStatus());
        }
    };

//_Constructors_____________________________________________________________________________________________

    public Bet() {}

    public Bet (Team teamWinner, double money,User user,Game game){
        this.game=game;
        //this.winningTeamId=teamWinner.getTeam_id();
        this.winningTeam=teamWinner;
        this.money=money;
        this.user=user;
    }

//___Getters and Setters________________________________________________________________________________________

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /*public Integer getWinningTeamId() {
        return winningTeamId;
    }

    public void setWinningTeamId(Integer winningTeamId) {
        this.winningTeamId = winningTeamId;
    }*/

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    /*public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }*/
}
