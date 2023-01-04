package com.bogdan.RecyclerVewTest;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ThirdAdapter extends RecyclerView.Adapter<ThirdAdapter.ViewHolder>  {


    private final Context context;
    private List<Todo> mTodos;

    public ThirdAdapter(Context context, List<Todo> mDataSet) {


        this.context = context;
        mTodos = mDataSet;


    }

    public void swapData(List<Todo> list) {
        mTodos = list;
        notifyDataSetChanged();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView idTextView;
        public final TextView taskTextView;
        private final Context context;


        public ViewHolder(Context context, @NonNull View itemView) {
            super(itemView);

            this.context = context;
            idTextView = itemView.findViewById(R.id.tv_id);
            taskTextView = itemView.findViewById(R.id.tv_task);


/////////////////////////////////////////////////////////////////////////////////////
            //   ((MainActivity)context).setOnItemClickListener(() -> itemView.setBackgroundColor(Color.YELLOW));
////////////////////////////////////////////////////////////////////////////////////////////
        }


        public void bind(Todo item) {
            //TODO use itemView and set data

            idTextView.setText(String.valueOf(item.getId()));
            taskTextView.setText(item.getTask());


/////////////////////////////////////////////////////////////////////////////////////
            ((MainActivity)context).setOnItemClickListener((isChange) -> itemView.setBackgroundColor(Color.YELLOW));
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

        holder.bind(mTodos.get(position));



    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if(key.equals("id")){
                    //TODO lets update blink discount textView :)
                    TextView idTextView = holder.idTextView;
                    idTextView.setText(o.getString(key));

                }else if(key.equals("task")){

                    TextView taskTextView = holder.taskTextView;
                    taskTextView.setText(o.getString(key));


                    AnimatorSet as = new AnimatorSet();
                    as.play(ObjectAnimator.ofFloat(taskTextView, "translationX", 0))
                            .after(ObjectAnimator.ofFloat(taskTextView,"translationX",100));
                    as.start();
                    //TODO lets update and change price color for some time
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }





}
