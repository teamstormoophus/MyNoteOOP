package com.hulk.pandaminder.model;

import java.util.ArrayList;
import java.util.List;

public class TodoHouse extends  PandaEntity {

    private List<TodoList> todoLists;

    public TodoHouse(String title) {
        super(title);
        todoLists = new ArrayList<>();
    }

    public List<TodoList> getTodoLists(){
        return todoLists;
    }

    public void addTodoList(TodoList todoList){
        todoLists.add(todoList);
    }

    public void updateTodoList(TodoList todoList){
        for(int i = 0; i < todoLists.size(); i++){
            if(todoLists.get(i).getId().equals(todoList.getId())){
                todoLists.set(i, todoList);
                return;
            }
        }
    }

    public void deleteTodoList(TodoList todoList){
        for(int i = 0; i < todoLists.size(); i++){
            if(todoLists.get(i).getId().equals(todoList.getId())){
                todoLists.remove(i);
                return;
            }
        }
    }

    public List<TodoList> getImportantTodoList(){
        List<TodoList> todoImportant = new ArrayList<>();
        for(TodoList todoList : todoLists){
            if(todoList.getImportant()) todoImportant.add(todoList);
        }

        return todoImportant;
    }
}
