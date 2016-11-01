package gigafortress.progressbarexample;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener btnClick;

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    Button btn1;
    Button btn2;
    Toolbar toolBar;
    MaterialProgressBar materialProgressBar1;
    MaterialProgressBar materialProgressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = $(R.id.toolbar);
        setSupportActionBar(toolBar);
        btn1 = $(R.id.button);
        btn2 = $(R.id.button2);
        materialProgressBar1 = $(R.id.progressbar1);
        materialProgressBar2 = $(R.id.progressbar2);
        btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button:
                        materialProgressBar1.setIndeterminate(true);
//                        materialProgressBar2.setIndeterminate(true);
                        break;
                    case R.id.button2:
                        materialProgressBar1.setIndeterminate(false);
//                        materialProgressBar2.setIndeterminate(false);
                        break;
                }
            }
        };
        btn1.setOnClickListener(btnClick);
        btn2.setOnClickListener(btnClick);
    }
}
