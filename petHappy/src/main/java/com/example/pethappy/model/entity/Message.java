package com.example.pethappy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;


@Entity
@Table(name = "messages")
public class Message extends BaseEntity{
    @Column(nullable = false)
     private String text;
    @Column(nullable = false)
     private LocalDate startOn;
    @Column(nullable = false)
     private LocalDate endOn;


    public Message(String text, LocalDate startOn, LocalDate endOn) {
        this.text = text;
        this.startOn = startOn;
        this.endOn = endOn;
    }

    public Message() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getStartOn() {
        return startOn;
    }

    public void setStartOn(LocalDate startOn) {
        this.startOn = startOn;
    }

    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}
