package com.bogdan.RecyclerVewTest;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;






public class BounceEdgeEffectFactory extends RecyclerView.EdgeEffectFactory {

    private static final float OVERSCROLL_TRANSLATION_MAGNITUDE = 0.9F;
    private static final float FLING_TRANSLATION_MAGNITUDE = 0.5F;
    private final Context context;
    private int mWidth;
    private int mHeight;
    private float translationYDelta;


///////////////////////////////////////////////////////
    public interface OnOverscrollListener {
        void onPullScroll(float dY);
        void onRelease();
        void onStartAnimation(float translationVelocity);
}

    // Define listener member variable
    private OnOverscrollListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnOverscrollListener(OnOverscrollListener listener) {
        this.listener = listener;
    }
////////////////////////////////////////////////////

    public BounceEdgeEffectFactory(Context context) {

        this.context = context;
    }


    @NotNull
    protected EdgeEffect createEdgeEffect(@NotNull final RecyclerView recyclerView, final int direction) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");

        return (EdgeEffect)(new EdgeEffect(recyclerView.getContext()) {


            // A reference to the [SpringAnimation] for this RecyclerView used to bring the item back after the over-scroll effect.
            @Nullable
            private SpringAnimation translationAnim;

            @Nullable
            private int deltaYScroll;

            @Nullable
            public  SpringAnimation getTranslationAnim() {
                return translationAnim;
            }

            public  void setTranslationAnim(@Nullable SpringAnimation var1) {
                translationAnim = var1;
            }



            public void onPull(float deltaDistance) {
                //onPull() is called when the user is pulling the content more than the limit of the
                //recyclerView. In this case, we’ll translate the whole recyclerView.

                super.onPull(deltaDistance);



                handlePull(deltaDistance);
            }

            public void onPull(float deltaDistance, float displacement) {
                super.onPull(deltaDistance, displacement);




                handlePull(deltaDistance);
            }

            private void handlePull(float deltaDistance) {
                // This is called on every touch event while the list is scrolled with a finger.

                // Translate the recyclerView with the distance

                int sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                translationYDelta = (float)(sign * recyclerView.getWidth()) * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE;

                recyclerView.setTranslationY(recyclerView.getTranslationY() + translationYDelta);

                if (listener != null) {
                        listener.onPullScroll(recyclerView.getTranslationY() + translationYDelta);
                }

                if (translationAnim != null) {
                    translationAnim.cancel();
                }
            }







            public void onRelease() {
                super.onRelease();
                // The finger is lifted. Start the animation to bring translation back to the resting state.

                if (listener != null) {
                    listener.onRelease();
                }


                if (recyclerView.getTranslationY() != 0F) {

                    translationAnim = createAnim();
                    if (translationAnim != null) {
                        translationAnim.start();
                    } else {
                        translationAnim = null;
                    }
                }
            }



            public void onAbsorb(int velocity) {
                super.onAbsorb(velocity);

                // The list has reached the edge on fling.
                int sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                float translationVelocity = (float)(sign * velocity) * FLING_TRANSLATION_MAGNITUDE;
                if (translationAnim != null) {
                    translationAnim.cancel();
                }

                if (listener != null) {
                    listener.onStartAnimation(translationVelocity);
                }

                translationAnim = createAnim().setStartVelocity(translationVelocity);
                if (translationAnim != null){
                    translationAnim.start();
                }
            }




            @Override
            public boolean draw(Canvas canvas) {
               // return super.draw(canvas);
                // don't paint the usual edge effect
                //return false;

              /*  super.draw(canvas);
                if ( translationYDelta > 0) {
                    canvas.drawRect(0, translationYDelta, 500, 0,
                            new Paint());
                }
                return true;*/

                return false;


            }

            public boolean isFinished() {
                // Without this, will skip future calls to onAbsorb()
                return translationAnim != null ? !translationAnim.isRunning() : true;
            }

            private SpringAnimation createAnim() {
                return (new SpringAnimation(recyclerView, (FloatPropertyCompat)SpringAnimation.TRANSLATION_Y))
                        .setSpring((new SpringForce()).setFinalPosition(0.0F).setDampingRatio(0.9F).setStiffness(500.0F));
            }
        });
    }
}






