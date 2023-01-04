package com.bogdan.RecyclerVewTest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SecondAdapter
        extends ListAdapter<Todo, SecondAdapter.SecondViewHolder> {

    private Interaction interaction;

    protected SecondAdapter() {
        super(new TodoDC());
       // this.interaction = interaction;
    }

    @NonNull
    @Override
    public SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SecondViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false),
                interaction);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    void swapData(List<Todo> data) {
        submitList(data);
    }

    @Override
    public void submitList(final List<Todo> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Interaction interaction;

        SecondViewHolder(View inflate, Interaction interaction) {
            super(inflate);
            this.interaction = interaction;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            Todo clicked = getItem(getAdapterPosition());
            switch (v.getId()) {
                //TODO handle clicks
                default:
            }
        }

        public void bind(Todo item) {
            //TODO use itemView and set data

            TextView tvTask = itemView.findViewById(R.id.tv_task);
            TextView tvID = itemView.findViewById(R.id.tv_id);

            tvTask.setText(item.getTask());
            tvID.setText(String.valueOf(item.getId()));

        }
    }

    interface Interaction {
    }

    private static class TodoDC extends DiffUtil.ItemCallback<Todo> {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem,
                                       @NonNull Todo newItem) {
            //TODO "not implemented"
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem,
                                          @NonNull Todo newItem) {
            //TODO "not implemented"
            return false;
        }
    }
}