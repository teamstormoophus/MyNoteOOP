package com.hulk.pandaminder.ui.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hulk.pandaminder.MainActivity;
import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.TodoHouse;
import com.hulk.pandaminder.model.TodoFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class TodoFactoryFragment extends Fragment {

    private ArrayAdapter<TodoHouse> arrayAdapter;

    private TodoFactory todoFactory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(this.getClass().getSimpleName() + "OnCreate");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo_factory, container, false);

        FloatingActionButton fab = root.findViewById(R.id.add_todo_house);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_new_entity);
                dialog.setTitle("NEW TODO HOUSE");

                ImageButton createList = dialog.findViewById(R.id.create_new);
                createList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get new TodoHouse
                        EditText listTitle = dialog.findViewById(R.id.title);
                        String title = listTitle.getText().toString();

                        if(!title.isEmpty()){

                            TodoHouse todoHouse = new TodoHouse(title);
                            todoFactory.addTodoHouse(todoHouse);

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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(this.getClass().getSimpleName() + " OnResume");

        todoFactory = ((MainActivity) Objects.requireNonNull(getActivity())).getTodoFactory();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, todoFactory.getTodoHouses());
        ListView listView = this.getActivity().findViewById(R.id.todo_houses);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoHouse todoHouse = (TodoHouse) parent.getItemAtPosition(position);

                Intent i = new Intent(getActivity(), TodoHouseActivity.class);
                i.putExtra("todo_house", todoHouse);
                i.putExtra("todo_factory", todoFactory);

                startActivity(i);
            }
        });

        EditText listSearch = this.getActivity().findViewById(R.id.todo_house_search);
        listSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(this.getClass().getSimpleName() + "OnDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println(this.getClass().getSimpleName() + "OnStop");
    }

}