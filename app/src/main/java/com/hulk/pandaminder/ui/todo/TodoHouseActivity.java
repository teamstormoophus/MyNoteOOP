package com.hulk.pandaminder.ui.todo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hulk.pandaminder.MainActivity;
import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.TodoFactory;
import com.hulk.pandaminder.model.TodoHouse;
import com.hulk.pandaminder.model.TodoList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TodoHouseActivity extends AppCompatActivity {

    Intent intent;
    private ArrayAdapter<TodoList> arrayAdapter;
    private TodoHouse todoHouse;
    private TodoFactory todoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_house);

        intent = getIntent();
        todoHouse = (TodoHouse) intent.getSerializableExtra("todo_house");
        todoFactory = (TodoFactory) intent.getSerializableExtra("todo_factory");

        FloatingActionButton fab = findViewById(R.id.add_todo_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_new_entity);
                dialog.setTitle("NEW TODO LIST");

                ImageButton createTodoList = dialog.findViewById(R.id.create_new);
                createTodoList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get new TodoList
                        EditText taskTitle = dialog.findViewById(R.id.title);
                        String title = taskTitle.getText().toString();

                        if(!title.isEmpty()){
                            TodoList todoList = new TodoList(title);

                            // Update Data
                            todoHouse.addTodoList(todoList);
                            todoFactory.updateTodoHouse(todoHouse);

                            // Update Data to ListView
                            arrayAdapter.notifyDataSetChanged();
                        }

                        dialog.dismiss();
                    }
                });

                ImageButton cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Write to File
        MainActivity.writeDataToFile(this, MainActivity.TODO_DATA_FILE_NAME, todoFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Read Data
        todoFactory = new TodoFactory();
        try {
            FileInputStream inputFile = openFileInput(MainActivity.TODO_DATA_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(inputFile);

            todoFactory = (TodoFactory) objectIn.readObject();

            objectIn.close();
            inputFile.close();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

        todoHouse = todoFactory.getTodoHouseById(todoHouse.getId());

        arrayAdapter = new TodoHouseAdapter(this, R.layout.todo_houses_list_view, android.R.id.text1, todoHouse.getTodoLists());

        final ListView listView = findViewById(R.id.todo_lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(TodoHouseActivity.this, "Clicked at position = " + position, Toast.LENGTH_SHORT).show();
                final TodoList todoList = (TodoList) parent.getItemAtPosition(position);

                Intent i = new Intent(TodoHouseActivity.this, TodoListActivity.class);
                i.putExtra("todo_list", todoList);
                i.putExtra("todo_house", todoHouse);
                i.putExtra("todo_factory", todoFactory);

                startActivity(i);
            }
        });
    }
}