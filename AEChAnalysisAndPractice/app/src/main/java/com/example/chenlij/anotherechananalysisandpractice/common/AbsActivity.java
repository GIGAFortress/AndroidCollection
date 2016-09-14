package com.example.chenlij.anotherechananalysisandpractice.common;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chenlij.anotherechananalysisandpractice.R;

/**
 * Created by Chenlij on 2016/9/5.
 */
public class AbsActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(layoutResId);
        getSupportActionBar().hide();
    }

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }
}
