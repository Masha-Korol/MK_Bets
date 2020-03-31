package com.example.stavki.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Manager extends User{
    private @Id @GeneratedValue Long id;

    Manager(){}

    Manager(String name){
        super(name);
    }

    public void pay(){
        //логика
    }

    public void addGame(Game game){
        //логика
    }
}
