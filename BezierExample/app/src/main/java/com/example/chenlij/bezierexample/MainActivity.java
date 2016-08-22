package com.example.chenlij.bezierexample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonM1;
    private BezierLayout bezierLayout;
    private Button buttonM2;
    Boolean flag;
    private Handler handler = new Handler();

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if(flag == true){
                handler.postDelayed(this, 50);      //设置延迟时间
            }

            /*需要执行的代码*/
            bezierLayout.addKeda();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();       //隐藏AppCompatActivity的标题
        initializedView();
    }

    private void initializedView() {
        buttonM1 = (Button)findViewById(R.id.buttonm1);
        buttonM2 = (Button)findViewById(R.id.buttonm2);
        buttonM1.setOnClickListener(this);
        buttonM2.setOnClickListener(this);
        bezierLayout = (BezierLayout)findViewById(R.id.bezierLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonm1:
                Toast.makeText(this, "buttonm1", Toast.LENGTH_SHORT).show();
                handler.post(task);
                flag = true;
                break;
            case R.id.buttonm2:
                Toast.makeText(this, "buttonm2", Toast.LENGTH_SHORT).show();
                flag = false;
                break;
            default:
                Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();
        }
    }
}

