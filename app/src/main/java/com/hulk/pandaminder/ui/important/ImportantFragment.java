package com.hulk.pandaminder.ui.important;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hulk.pandaminder.MainActivity;
import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.TodoFactory;
import com.hulk.pandaminder.model.TodoList;
import com.hulk.pandaminder.ui.todo.TodoHouseAdapter;
import com.hulk.pandaminder.ui.todo.TodoListActivity;

import java.util.Objects;

public class ImportantFragment extends Fragment {

    private ArrayAdapter<TodoList> arrayAdapter;
    private TodoFactory todoFactory;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_important, container, false);

        FloatingActionButton fab = root.findViewById(R.id.add_todo_list);
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
                        TodoList todoList = new TodoList(title);
                        todoList.setImportant(true);

                        dialog.dismiss();

                        // Update Data to ListView
                        arrayAdapter.notifyDataSetChanged();
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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        todoFactory = ((MainActivity) Objects.requireNonNull(getActivity())).getTodoFactory();


        arrayAdapter = new TodoHouseAdapter(this.getActivity(), R.layout.todo_houses_list_view, android.R.id.text1, todoFactory.getImportantTodoList());
        ListView listView = this.getActivity().findViewById(R.id.todo_lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList todoList = (TodoList) parent.getItemAtPosition(position);

                Intent i = new Intent(getActivity(), TodoListActivity.class);
                i.putExtra("todo_list", todoList);
                i.putExtra("todo_factory", todoFactory);

                startActivity(i);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println(this.getClass().getSimpleName() + " OnPause");

        // Write to File
        MainActivity.writeDataToFile(this.getActivity(), MainActivity.TODO_DATA_FILE_NAME, todoFactory);
    }
}