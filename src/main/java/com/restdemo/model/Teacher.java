package com.restdemo.model;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String password;

    public Teacher(){}

    public Teacher(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
