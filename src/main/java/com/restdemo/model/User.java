package com.restdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    private String name;
    @Column
    private String password;

    public User(){}

    public User(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
