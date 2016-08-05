package com.example.chenlij.bezierexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonM1;
    private BezierLayout bezierLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializedView();
    }

    private void initializedView() {
        buttonM1 = (Button)findViewById(R.id.buttonm1);
        buttonM1.setOnClickListener(this);
        bezierLayout = (BezierLayout)findViewById(R.id.bezierLayout);
    }

    @Override
    public void onClick(View view) {
        bezierLayout.addKeda();
    }
}
