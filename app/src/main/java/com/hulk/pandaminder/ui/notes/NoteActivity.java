package com.hulk.pandaminder.ui.notes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.hulk.pandaminder.MainActivity;
import com.hulk.pandaminder.R;
import com.hulk.pandaminder.model.Note;
import com.hulk.pandaminder.model.NoteFactory;

public class NoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteContent;
    private ImageButton noteDelete;

    Intent intent;

    private Note note;
    private NoteFactory noteFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        intent = this.getIntent();
        note = (Note) intent.getSerializableExtra("note");
        noteFactory = (NoteFactory) intent.getSerializableExtra("noteFactory");

        noteTitle = findViewById(R.id.note_title);
        noteTitle.setText(note.getTitle());

        noteContent = findViewById(R.id.note_content);
        noteContent.setText(note.getContent());

        noteDelete = findViewById(R.id.note_delete);
        noteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.setTitle("Are you sure about that ? Please confirm this !!!");

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
                        noteFactory.deleteNote(note);
                        dialog.dismiss();

                        intent = new Intent(NoteActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        note.setTitle(noteTitle.getText().toString());
        note.setContent(noteContent.getText().toString());

        // Update note to NoteFactory
        noteFactory.updateNote(note);

        // Write to File
        MainActivity.writeDataToFile(this, MainActivity.NOTE_DATA_FILE_NAME, noteFactory);
    }
}