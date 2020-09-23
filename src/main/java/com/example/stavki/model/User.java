package com.example.stavki.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    public static final String ROLE_CLIENT = "CLIENT";
    public static final String ROLE_MANAGER = "MANAGER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "money")
    private double money;

    @Column(name = "password")
    private String password;

    private Integer wins;

    private Integer losses;

    @Column(name = "role")
    private String role;

    //mappedBy указывает на соответствующую переменную в другом классе
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bet> betsList;

//__Constructors___________________________________________________________________________________________________

    public User(){}

    public User(String name, String password, String role){
        this.name=name;
        this.password=password;
        this.wins=0;
        this.losses=0;
        if(role.equals("CLIENT")){
            this.role=ROLE_CLIENT;
        }
        else{
            this.role=ROLE_MANAGER;
        }
    }

//____________________________________________________________________________________________-

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//__Getters and Setters_____________________________________________________________________________________________

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() { return money; }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Bet> getBetsList() {
        return betsList;
    }

    public void setBetsList(List<Bet> betsList) {
        this.betsList = betsList;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
