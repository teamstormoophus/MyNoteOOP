package com.hulk.pandaminder.model;

public class TodoChild extends PandaEntity {
    private boolean isDone;

    public TodoChild(String title) {
        super(title);
    }

    public void setDone(boolean isDone){
        this.isDone = isDone;
    }

    public boolean getDone(){
        return this.isDone;
    }
}
