package com.example.notetakingapp;

import static android.app.ProgressDialog.show;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Add_Note_Activity extends AppCompatActivity {
    private EditText etNoteTitle, etNoteContent;
    private Button btnSaveNote;
    private DatabaseHelper databaseHelper;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("note_id")) {
            noteId = intent.getIntExtra("note_id", -1);
            etNoteTitle.setText(intent.getStringExtra("note_title"));
            etNoteContent.setText(intent.getStringExtra("note_content"));
        }

        btnSaveNote.setOnClickListener(view -> {
            String title = etNoteTitle.getText().toString();
            String content = etNoteContent.getText().toString();

            if (!title.isEmpty() && !content.isEmpty()) {
                if (noteId == -1) {
                    databaseHelper.addNote(title, content);
                    makeText(this, "", Toast.LENGTH_SHORT).show();
                    makeText(this, "", Toast.LENGTH_SHORT).show();
                    makeText(this, "", Toast.LENGTH_SHORT).show();
                    makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.updateNote(noteId, title, content);
                    makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}