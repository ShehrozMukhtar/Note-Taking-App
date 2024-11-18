package com.example.notetakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton btnAddNote;
    private DatabaseHelper databaseHelper;
    private NoteAdapter adapter;
    private ArrayList<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
        databaseHelper = new DatabaseHelper(this);

        noteList = new ArrayList<>();
        adapter = new NoteAdapter(this,noteList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, Add_Note_Activity.class);
                intent.putExtra("note_id", note.getId());
                intent.putExtra("note_title", note.getTitle());
                intent.putExtra("note_content", note.getContent());
                startActivity(intent);
            }

            @Override
            public void onNoteDelete(int noteId) {
                databaseHelper.deleteNoteById(noteId);
                loadNotes();
            }
        });

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);

        btnAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Add_Note_Activity.class);
            startActivity(intent);
        });

        loadNotes();
    }

    private void loadNotes() {
        noteList.clear();
        Cursor cursor = databaseHelper.getAllNotes();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                noteList.add(new Note(id, title, content));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}
