package com.example.stavki.model;

import com.example.stavki.services.GameService;
import com.example.stavki.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "games")
public class Game{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team1;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team2;

    @OneToOne(fetch = FetchType.EAGER)
    private Team winner;

    // CascadeType.remove -> при удалении игры удаляются все ставки, сделанные на нее
    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private List<Bet> betList;

    /*@ManyToMany(fetch = FetchType.EAGER,mappedBy = "games")
    private List<Team> twoTeams;*/

//___Methods_________________________________________________________________________________________

    public void addBetToList(Bet bet) {betList.add(bet);}

    public static Comparator<Game> statusComparator = new Comparator<Game>() {
        @Override
        public int compare(Game o1, Game o2) {
            return o1.getStatus().compareTo(o2.getStatus());
        }
    };

//___Constructors______________________________________________________________________________________________

    public Game() {}

    public Game(Team team1,Team team2,String name, Team teamNull) {
        this.team1=team1;
        this.team2=team2;
        //this.winnerName="<null>";
        this.winner=teamNull;
        this.status=Status.AVAILABLE;
        this.name=name;
        }

//___Getters and Setters_________________________________________________________________________________

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Bet> getBetList() {
        return betList;
    }

    public void setBetList(List<Bet> betList) {
        this.betList = betList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }
}
