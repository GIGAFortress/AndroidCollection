package gigafortress.asynctaskjson;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import gigafortress.asynctaskjson.Adapter.NewsAdapter;

public class MainActivity extends AppCompatActivity {

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }
//    NewsAdapter newsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listview1.setAdapter(newsAdapter);    直接设置一个空的Adapter会报空指针
        initID();
    }

    public ListView listview1;
    private void initID() {
        listview1 = $(R.id.listview1);

    }
}
