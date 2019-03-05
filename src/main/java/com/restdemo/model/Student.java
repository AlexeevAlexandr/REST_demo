package com.restdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String password;

    public Student(){}

    public Student(String username, String name, String password) {
        this.setUsername(username);
        this.setName(name);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
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