package com.hulk.pandaminder.ui.notes;

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
import com.hulk.pandaminder.model.Note;
import com.hulk.pandaminder.model.NoteFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class NotesFragment extends Fragment {
    ListView listView;
    ArrayAdapter<Note> arrayAdapter;

    private NoteFactory noteFactory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        FloatingActionButton fab = root.findViewById(R.id.add_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_new_entity);
                dialog.setTitle("NEW NOTE");

                ImageButton createList = dialog.findViewById(R.id.create_new);
                createList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get new note
                        EditText listTitle = dialog.findViewById(R.id.title);
                        String title = listTitle.getText().toString();
                        if(!title.isEmpty()){
                            Note note = new Note(title);
                            noteFactory.addNote(note);

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
        System.out.println(this.getClass().getSimpleName() + "OnResume");

        noteFactory = ((MainActivity)this.getActivity()).getNoteFactory();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, noteFactory.getNoteList());


        listView = this.getActivity().findViewById(R.id.note_lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) parent.getItemAtPosition(position);

                Intent i = new Intent(getActivity(), NoteActivity.class);
                i.putExtra("note", note);
                i.putExtra("noteFactory", noteFactory);

                startActivity(i);
            }
        });

        EditText noteSearch = this.getActivity().findViewById(R.id.note_search);
        noteSearch.addTextChangedListener(new TextWatcher() {
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
        System.out.println(this.getClass().getSimpleName() + "OnPause");

        // Write to File
        MainActivity.writeDataToFile(this.getActivity(), MainActivity.NOTE_DATA_FILE_NAME, noteFactory);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}