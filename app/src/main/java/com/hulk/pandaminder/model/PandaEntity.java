package com.hulk.pandaminder.model;

import java.io.Serializable;
import java.util.UUID;

public class PandaEntity implements Serializable {
    protected String id;
    protected String title;

    public PandaEntity(String title){
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
