package com.bogdan.RecyclerVewTest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    List<Todo> mDataSet;
    boolean isChange = true;

    ///////////////////////////////////////////////////////
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(boolean isChange);
    }

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
//////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDataSet = IntStream.rangeClosed(0, 30).mapToObj(x -> new Todo(x, "Todo" + x)).collect(Collectors.toList());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayout topView = findViewById(R.id.top_view);

        MyAdapter adapter = new MyAdapter(this, mDataSet);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BounceEdgeEffectFactory pullEffect = new BounceEdgeEffectFactory(this);
        recyclerView.setEdgeEffectFactory(pullEffect);
        pullEffect.setOnOverscrollListener(new BounceEdgeEffectFactory.OnOverscrollListener() {
            float velocity, dY;
            @Override
            public void onPullScroll(float dY) {
                this.dY = dY;
                topView.getLayoutParams().height = (int) dY;
                topView.requestLayout();
            }

            @Override
            public void onRelease() {
                SpringAnimation anim = new SpringAnimation(topView, (FloatPropertyCompat)SpringAnimation.TRANSLATION_Y);
                anim.setSpring((new SpringForce())
                        .setFinalPosition(-dY)
                        .setDampingRatio(1.0F)
                        .setStiffness(400.0F))
                        .setStartVelocity(0)  //.setStartVelocity(velocity)
                        .addEndListener((animation, canceled, value, velocity) -> {
                            topView.setTranslationY(0);
                            dY=0;
                        })
                        .start();

                if (dY>300) dialog();

                }


            @Override
            public void onStartAnimation(float translationVelocity) {
                 velocity = translationVelocity;
            }
        });

        adapter.submitList(mDataSet);








        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // mDataSet.add(new Todo("ToDo Some"));

//            mDataSet = new ArrayList<>(
//                    Arrays.asList(new Todo ( 1,"ffwwtfff"),
//                            new Todo ( 2,"jhkl"),
//                            new Todo ( 3,"ffffff"),
//                            new Todo ( 4,"ffhj4, wqq"),
//                            new Todo ( 5,"ffffff"))) ;

            int[] rnd = new Random().ints(16, 1, 16).limit(16).toArray();
            IntStream.rangeClosed(0, 15).forEach(x -> mDataSet.set(x, new Todo(x, "Todo" + rnd[x])));
            //  IntStream.rangeClosed(0,15).forEach(x-> mDataSet.set(x, new Todo(rnd[x], "Todo")));

//            isChange = !isChange;
//
//            IntStream.range(0,mDataSet.size()).forEach(x-> {
//                Todo temp = new Todo(x, mDataSet.get(x).getTask(), isChange);
//                mDataSet.set(x, temp);
//            });

//            IntStream.range(0, mDataSet.size()).forEach(x -> {
//                Todo temp = mDataSet.get(x);
//                temp.setSelected(true);
//                mDataSet.set(x, temp);
//            });

            adapter.swapData(mDataSet);




/////////////////////////////////////////////////////////////
            if (listener != null) {
                listener.onItemClick(isChange);
            }
//////////////////////////////////////////////////////////////

        });


    }

    private void dialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void initializeSecond() {
        setContentView(R.layout.activity_main_2);

        TextView tv = findViewById(R.id.tv);
        TextView tv1 = findViewById(R.id.tv1);

        CardView card = findViewById(R.id.card);

        Drawable background = card.getForeground();
        RippleDrawable rippleDrawable = (RippleDrawable) background;
        rippleDrawable.setHotspot(0, 0);


        card.setOnClickListener(view ->
//                rippleDrawable.setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}));
                {
                    rippleDrawable.setState(new int[]{android.R.attr.state_first});
                    rippleDrawable.jumpToCurrentState();
                }

        );
    }
}