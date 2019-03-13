package com.restdemo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String teacher;

    public Student(){}

    public Student(String username, String name, String teacher) {
        this.setUsername(username);
        this.setName(name);
        this.setTeacher(teacher);
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}