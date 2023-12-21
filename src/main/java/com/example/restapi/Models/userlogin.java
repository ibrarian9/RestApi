package com.example.restapi.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_login")
public class userlogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(nullable = false, unique = true, name = "username")
    private String username;
    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @Column(nullable = false)
    private String password;

    public userlogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public userlogin() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
