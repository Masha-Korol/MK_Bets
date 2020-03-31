package com.example.stavki.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class Team {
    //1-victory, 0-failure
    private List<Boolean> historyOfVictories;
    private @Id @GeneratedValue Long id;
    private String name;
    private float winningRate;

    public Team(){}

    Team(String name) {
        this.name = name;
        historyOfVictories = new LinkedList<>();
        winningRate = 1;

    }
}
