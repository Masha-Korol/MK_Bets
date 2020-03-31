package com.example.stavki.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class Client extends User{
    private @Id @GeneratedValue Long id;
    List<Long> current;
    List<Long> history;

    Client(){}

    Client(String name){
        super(name);
        List<Long> current = new LinkedList<>();
        List<Long> history = new LinkedList<>();
    }

    public void bet(){
        //логика
    }
}
