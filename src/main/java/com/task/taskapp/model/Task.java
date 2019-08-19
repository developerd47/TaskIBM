package com.task.taskapp.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;


@Entity
//Our Entity(Table)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //generate id automatically.
    private long id;
    private String name;
    private String description;
    private Date dueDate;
    private String status;

    //Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task() {
    }

    public Task(long id, String name, String description, Date dueDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }
}
