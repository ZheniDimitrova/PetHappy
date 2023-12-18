package com.example.pethappy.model.dto;

import java.time.LocalDate;

public class MessageImportDto {

    private String text;

    private LocalDate startOn;
    private LocalDate endOn;


    public MessageImportDto() {
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
