package com.bogdan.RecyclerVewTest;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class CustomNumberPicker extends NumberPicker {

   private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;


    public CustomNumberPicker(Context context) {
        super(context);

    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        super.setWrapSelectorWheel(wrapSelectorWheel);
    }
}