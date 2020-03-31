package com.example.stavki.entities;


import java.math.BigDecimal;

public abstract class User {
    private String name;
    private BigDecimal money;

    User(){}

    User(String name){
        this.name = name;
        this.money = new BigDecimal(1000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void addMoney(BigDecimal money){
        this.money = this.money.add(money);
    }


}
