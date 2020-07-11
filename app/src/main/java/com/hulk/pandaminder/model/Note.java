package com.hulk.pandaminder.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Note extends PandaEntity {
    private String content;
    private Date date;

    public Note(String title){
        super(title);
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}