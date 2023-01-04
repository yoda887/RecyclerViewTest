package com.bogdan.RecyclerVewTest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

class MyAdapter extends ListAdapter<Todo, MyAdapter.ViewHolder> {


    private final Context context;
    private final List<Todo> mTodos;

    public MyAdapter(Context context, List<Todo> mDataSet) {
        super(DIFF_CALLBACK);

        this.context = context;
        mTodos = mDataSet;


    }

    public void swapData(List<Todo> list) {
        submitList(list);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView idTextView;
        public final TextView taskTextView;
        private final Context context;
        private final Chip chipView;


        public ViewHolder(Context context, @NonNull View itemView) {
            super(itemView);

            this.context = context;
            idTextView = itemView.findViewById(R.id.tv_id);
            taskTextView = itemView.findViewById(R.id.tv_task);
            chipView = itemView.findViewById(R.id.chip);

        }


        public void bind(Todo item) {
            //TODO use itemView and set data

            idTextView.setText(String.valueOf(item.getId()));
            taskTextView.setText(item.getTask());

            if (item.isSelected()) {
                itemView.setBackgroundColor(Color.YELLOW);
                chipView.setVisibility(VISIBLE);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
                chipView.setVisibility(GONE);
            }


///////////////////////////////////////////////////////////////////////////////////////
//            ((MainActivity)context).setOnItemClickListener(isChange -> {
//
//                if (!isChange) {
//                    itemView.setBackgroundColor(Color.YELLOW);
//                } else {
//                    itemView.setBackgroundColor(Color.WHITE);
//                }
//            });
////////////////////////////////////////////////////////////////////////////////////////////

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(context, contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(getItem(position));
           
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                switch (key) {
                    case "id":
                        //TODO lets update blink discount textView :)
                        TextView idTextView = holder.idTextView;
                        idTextView.setText(o.getString(key));

                        break;
                    case "task":
                        //TODO lets update and change price color for some time
                        TextView taskTextView = holder.taskTextView;
                        taskTextView.setText(o.getString(key));


                        AnimatorSet as = new AnimatorSet();
                        as.play(ObjectAnimator.ofFloat(taskTextView, "translationX", 0))
                                .after(ObjectAnimator.ofFloat(taskTextView, "translationX", 100));
                        as.start();

                        break;
                    case "isSelected":
                        //TODO lets update and change price color for some time

                        Chip ch = holder.chipView;

                        if (o.getBoolean(key)) {
                            holder.itemView.setBackgroundColor(Color.YELLOW);
                            ObjectAnimator.ofFloat(ch, "scaleX", 0,1).start();
                            ch.setVisibility(VISIBLE);
                        } else {
                            holder.itemView.setBackgroundColor(Color.WHITE);

                            //ObjectAnimator.ofFloat(ch, "scaleX", 1,0).start();

                            ch.animate()
                                    .scaleX(0f)
                                    .withEndAction(() -> {
                                        ch.setVisibility(GONE);
                                    })
                                    .start();
                        }

                        break;
                }
            }
        }
    }

    @Override
    public void submitList(final List<Todo> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }




    public static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Todo>() {

        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTask().equals(newItem.getTask())
                    && oldItem.isSelected() == newItem.isSelected();
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Todo oldItem, @NonNull Todo newItem) {


            Bundle diff = new Bundle();
            if (newItem.getId() != (oldItem.getId())) {
                diff.putInt("id", newItem.getId());
            }
            if (!newItem.getTask().equals(oldItem.getTask())) {
                diff.putString("task", newItem.getTask());
            }
            if (newItem.isSelected() != (oldItem.isSelected())) {
                diff.putBoolean("isSelected", newItem.isSelected());
            }
            if (diff.size() == 0) {
                return null;
            }
            return diff;

            //return super.getChangePayload(oldItem, newItem);
        }
    };


}
