package com.hulk.pandaminder.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoList extends TodoChild {
    private List<TodoChild> todoChildren;
    private String content;
    private boolean isToday;
    private boolean isImportant;
    private Date remindTime;
    private String[] tags;

    public TodoList(String title){
        super(title);
        todoChildren = new ArrayList<>();
    }

    public List<TodoChild> getTodoChildren(){
        return todoChildren;
    }

    public void addTodoChild(TodoChild todoChild){
        todoChildren.add(todoChild);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public boolean getIsToday() {
        return isToday;
    }

    public void setImportant(boolean important){
        this.isImportant = important;
    }

    public boolean getImportant() {
        return isImportant;
    }
}
