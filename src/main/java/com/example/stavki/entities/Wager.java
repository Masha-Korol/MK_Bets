package com.example.stavki.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Wager {
    private @Id Long gameFK;
    private @Id Long clientFK;
    private BigDecimal money;
    private boolean isFirstTeamWinning;

    Wager(){}

    public Wager(Client client, Game game, BigDecimal money, boolean isFirstTeamWinning){
        this.gameFK = game.getId();
        this.clientFK = client.getId();
        this.money = money;
        this.isFirstTeamWinning = isFirstTeamWinning;
    }
}
