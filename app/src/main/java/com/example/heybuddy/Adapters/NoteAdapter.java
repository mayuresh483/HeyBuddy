package com.example.heybuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heybuddy.Models.Note;
import com.example.heybuddy.R;
import com.example.heybuddy.databinding.NoteSamplesBinding;

import javax.security.auth.callback.Callback;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.viewHolder> {

    public NoteAdapter(){
        super(Callback);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_samples,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Note notes = getItem(position);
        holder.binding.noteTitle.setText(notes.getTitle());
        holder.binding.noteDesc.setText(notes.getDesc());
    }

    public Note getNote(int position) {
        return super.getItem(position);
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        NoteSamplesBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NoteSamplesBinding.bind(itemView);
        }
    }

    private static final DiffUtil.ItemCallback<Note> Callback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDesc().equals(newItem.getDesc());
        }
    };
}
