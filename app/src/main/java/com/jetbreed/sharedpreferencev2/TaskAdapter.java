package com.jetbreed.sharedpreferencev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<String> tasks;
    private OnTaskDeleteListener deleteListener;

    public interface OnTaskDeleteListener {
        void onDelete(int position);
    }

    public TaskAdapter(ArrayList<String> tasks, OnTaskDeleteListener listener) {
        this.tasks = tasks;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, deleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.tvTask.setText(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTask;
        ImageButton btnDelete;

        TaskViewHolder(View itemView, OnTaskDeleteListener listener) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onDelete(pos);
                    }
                }
            });
        }
    }
}

