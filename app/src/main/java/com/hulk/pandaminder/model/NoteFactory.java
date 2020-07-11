package com.hulk.pandaminder.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteFactory implements Serializable {
    private List<Note> noteList;

    public NoteFactory(){
        noteList = new ArrayList<>();
    }

    public void addNote(Note note){
        this.noteList.add(note);
    }

    public void updateNote(Note note){
        for(int i = 0; i < noteList.size(); i++){
            if(noteList.get(i).getId().equals(note.getId())){
                noteList.set(i, note);
            }
        }
    }

    public void deleteNote(Note note){
        for(int i = 0; i < noteList.size(); i++){
            if(noteList.get(i).getId().equals(note.getId())){
                noteList.remove(i);
            }
        }
    }

    public List<Note> getNoteList(){
        return noteList;
    }

    @NonNull
    @Override
    public String toString() {
        String res = "";
        for(int i = 0; i < noteList.size(); i++){
            res += noteList.get(i).getTitle();
        }
        return res;
    }
}