package com.hulk.pandaminder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hulk.pandaminder.model.NoteFactory;
import com.hulk.pandaminder.model.TodoFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    private TodoFactory todoFactory;
    private NoteFactory noteFactory;

    public static String TODO_DATA_FILE_NAME = "todoFile";
    public static String NOTE_DATA_FILE_NAME = "noteFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notes, R.id.nav_important, R.id.nav_tasks)
                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Read File
//        readTodoFactory();
//        readNoteFactory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(this.getClass().getSimpleName() + "OnResume");

        // Read File
        readTodoFactory();
        readNoteFactory();
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println(this.getClass().getSimpleName() + "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(this.getClass().getSimpleName() + "OnDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void readTodoFactory(){
        todoFactory = new TodoFactory();
        try {
            FileInputStream inputFile = openFileInput(TODO_DATA_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(inputFile);

            todoFactory = (TodoFactory) objectIn.readObject();

            objectIn.close();
            inputFile.close();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    private void readNoteFactory(){
        noteFactory = new NoteFactory();
        try{
            FileInputStream inputFile = openFileInput(NOTE_DATA_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(inputFile);

            noteFactory = (NoteFactory) objectIn.readObject();

            objectIn.close();
            inputFile.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TodoFactory getTodoFactory() {
        return todoFactory;
    }

    public NoteFactory getNoteFactory(){
        return noteFactory;
    }

    public static void writeDataToFile(Context context, String fileName, Object object){
        FileOutputStream fos;
        try {
            fos = Objects.requireNonNull(context).openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(object);

            objectOut.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}