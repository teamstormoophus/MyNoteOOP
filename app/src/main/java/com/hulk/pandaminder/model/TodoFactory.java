package com.hulk.pandaminder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TodoFactory implements Serializable {
    private List<TodoHouse> todoHouses;

    public TodoFactory(){
        todoHouses = new ArrayList<>();
    }

    public void addTodoHouse(TodoHouse todoHouse){
        todoHouses.add(todoHouse);
    }

    public List<TodoHouse> getTodoHouses(){
        return this.todoHouses;
    }

    @Override
    public String toString() {
        String res = "Factory: ";

        for(TodoHouse todoHouse : todoHouses){
            res += todoHouse.toString() + "\n";
        }

        return res;
    }

    public TodoHouse getTodoHouseById(String Id){
        for(int i = 0; i < todoHouses.size(); i++){
            if(todoHouses.get(i).getId().equals(Id)){
                return todoHouses.get(i);
            }
        }
        return null;
    }

    public void updateTodoHouse(TodoHouse todoHouse) {
        for(int i = 0; i < todoHouses.size(); i++){
            if(todoHouses.get(i).getId().equals(todoHouse.getId())) {
                todoHouses.set(i, todoHouse);
                return;
            }
        }
    }

    public List<TodoList> getImportantTodoList(){
        List<TodoList> todoImportant = new ArrayList<>();
        for(TodoHouse todoHouse : todoHouses){
            todoImportant.addAll(todoHouse.getImportantTodoList());
        }
        return todoImportant;
    }
}
