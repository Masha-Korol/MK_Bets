package com.example.stavki.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Game {
    private @Id @GeneratedValue Long id;
    private Status status;
    private Date deadline;
    private String team1;
    private String team2;
    private double rate;
    private boolean isFirstTeamWinning;

    Game(){}
    public Game(Date deadline, Team team1, Team team2){
        this.deadline = deadline;
        this.status = Status.AVAILABLE;
        this.team1 = team1.getName();
        this.team2 = team2.getName();
        //как нибудь исходя из команд определяем rate - коэффициент
    }
}
