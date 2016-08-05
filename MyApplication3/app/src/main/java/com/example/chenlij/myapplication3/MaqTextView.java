package com.example.chenlij.myapplication3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Chenlij on 2016/8/5.
 */
public class MaqTextView extends TextView {
    public MaqTextView(Context context) {
        super(context);
    }

    public MaqTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaqTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
