package com.example.notetakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter  extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private ArrayList<Note> noteList;
    private OnNoteClickListener listener;

    public NoteAdapter(Context context, ArrayList<Note> noteList, OnNoteClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());

        holder.itemView.setOnClickListener(view -> listener.onNoteClick(note));
        holder.btnDelete.setOnClickListener(view -> listener.onNoteDelete(note.getId()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
         TextView tvTitle, tvContent;
        ImageButton btnDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(Note note);

        void onNoteDelete(int noteId);
    }
}
