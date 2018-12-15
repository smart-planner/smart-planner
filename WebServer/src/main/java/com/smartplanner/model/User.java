package com.smartplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

}
