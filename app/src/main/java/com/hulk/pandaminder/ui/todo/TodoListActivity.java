package com.hulk.pandaminder.ui.todo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hulk.pandaminder.MainActivity;
import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.TodoChild;
import com.hulk.pandaminder.model.TodoFactory;
import com.hulk.pandaminder.model.TodoHouse;
import com.hulk.pandaminder.model.TodoList;

public class TodoListActivity extends AppCompatActivity {

    private ArrayAdapter<TodoChild> arrayAdapter;

    Intent intent;
    private TodoFactory todoFactory;
    private TodoHouse todoHouse;
    private TodoList todoList;

    private EditText noteContent;
    private RadioButton noteTitle;
    private  RadioButton noteImportant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        intent = getIntent();
        todoList = (TodoList) intent.getSerializableExtra("todo_list");
        todoHouse = (TodoHouse) intent.getSerializableExtra("todo_house");
        todoFactory = (TodoFactory) intent.getSerializableExtra("todo_factory");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, todoList.getTodoChildren());

        ListView listView = findViewById(R.id.todo_children);
        listView.setAdapter(arrayAdapter);

        noteTitle = findViewById(R.id.todo_list_title);
        noteTitle.setText(todoList.getTitle());
        noteTitle.setChecked(todoList.getDone());

        RadioButton isToday = findViewById(R.id.isToday);
        isToday.setChecked(todoList.getIsToday());

        noteImportant = findViewById(R.id.important);
        noteImportant.setChecked(todoList.getImportant());

        noteContent = findViewById(R.id.todo_list_content);
        noteContent.setText(todoList.getContent());

        final Button addStep = findViewById(R.id.add_step);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_new_entity);
                dialog.setTitle("NEW STEP");

                ImageButton createTodoList = dialog.findViewById(R.id.create_new);
                createTodoList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get new TodoList
                        EditText taskTitle = dialog.findViewById(R.id.title);
                        String title = taskTitle.getText().toString();

                        if(!title.isEmpty()){
                            TodoChild todoChild = new TodoChild(title);
                            todoList.addTodoChild(todoChild);

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


        ImageButton delete = findViewById(R.id.remove_todo_list);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.setTitle("Are you sure about that ? Please confirm this !!!");
                dialog.show();

                ImageButton cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ImageButton delete = dialog.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        todoHouse.deleteTodoList(todoList);
                        todoFactory.updateTodoHouse(todoHouse);

                        intent = new Intent(TodoListActivity.this, TodoHouseActivity.class);
                        intent.putExtra("todo_house", todoHouse);
                        intent.putExtra("todo_factory", todoFactory);

                        dialog.dismiss();

                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(this.getClass().getSimpleName() + " OnPause");

        todoList.setContent(noteContent.getText().toString());
        todoList.setDone(noteTitle.isChecked());
        todoList.setImportant(noteImportant.isChecked());

        // Update Data
        if(todoHouse != null){
            todoHouse.updateTodoList(todoList);
            todoFactory.updateTodoHouse(todoHouse);
        }

        // Write Data to File
        MainActivity.writeDataToFile(this, MainActivity.TODO_DATA_FILE_NAME, todoFactory);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(this.getClass().getSimpleName() + " OnDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println(this.getClass().getSimpleName() + " OnStop");
    }
}
