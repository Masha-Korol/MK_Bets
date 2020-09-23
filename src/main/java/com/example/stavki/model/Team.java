package com.example.stavki.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer team_id;

    @Column(name = "name")
    private String name;

    @Column(name = "sport")
    private String sport;

    @OneToMany(mappedBy = "winningTeam", cascade = CascadeType.REMOVE)
    private List<Bet> betsList;

    /*@ManyToMany
    @JoinTable(name = "all_games",
            joinColumns = @JoinColumn(name ="team_id",referencedColumnName = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id",referencedColumnName = "game_id")
    )
    private List<Game> games;*/

//__Constructors________________________________________________________________________________________

    public Team(){}
    public Team(String name, String sport){this.name=name;
    this.sport=sport;}

//__Getters and Setters____________________________________________________________________________________________

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public List<Bet> getBetsList() {
        return betsList;
    }

    public void setBetsList(List<Bet> betsList) {
        this.betsList = betsList;
    }
}


