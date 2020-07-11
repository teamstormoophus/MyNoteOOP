package com.hulk.pandaminder.ui.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.TodoList;

import java.util.List;

public class TodoHouseAdapter extends ArrayAdapter<TodoList> {

    private Context context;
    private List<TodoList> todoLists;

    private ImageButton isImportant;
    private RadioButton isDone;

    public TodoHouseAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<TodoList> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.todoLists = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final TodoList todoList = todoLists.get(position);

        System.out.println("GetView Here");

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.todo_houses_list_view, null);

        TextView houseTitle = convertView.findViewById(R.id.house_title);
        houseTitle.setText(todoList.getTitle());

        isDone = convertView.findViewById(R.id.is_done);
        isDone.setChecked(todoList.getDone());
//        System.out.println(todoList + " done is before type: " + todoList.getDone() + " " + isDone.isChecked());
        isDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(todoList + " done is before type: " + todoList.getDone() + " " + isDone.isChecked());
                boolean checking = isDone.isChecked();
                isDone.setChecked(!checking);
//                isDone.setText(checking ? "Checked" : "Unchecked");
                todoList.setDone(!checking);

//                System.out.println(todoList + " done is after type: " + todoList.getDone() + " " + isDone.isChecked());
            }
        });

        isImportant = convertView.findViewById(R.id.important);
        if(todoList.getImportant()) {
            isImportant.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            isImportant.setImageResource(android.R.drawable.btn_star_big_off);
        }

        isImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isImportant = todoList.getImportant();
                if(isImportant){
                    TodoHouseAdapter.this.isImportant.setImageResource(android.R.drawable.btn_star_big_off);
                    todoList.setImportant(false);
                }
                else{
                    TodoHouseAdapter.this.isImportant.setImageResource(android.R.drawable.btn_star_big_on);
                    todoList.setImportant(true);
                }
//                System.out.println(todoList + " important is: " + todoList.getImportant());
            }
        });

        return convertView;
    }
}
