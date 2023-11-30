package com.example.restapi.Models;

import jakarta.persistence.*;
import lombok.Getter;



@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

    public Role() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
