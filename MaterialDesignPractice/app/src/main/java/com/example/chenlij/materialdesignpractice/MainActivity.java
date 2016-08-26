package com.example.chenlij.materialdesignpractice;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().hide();       //隐藏AppCompatActivity的标题
//        setSupportActionBar(mToolbar);    //这个是不能用的，会报错
        setUpProfileImage();
    }
    private void setUpProfileImage() {
        View headerView=  mNavigationView.inflateHeaderView(R.layout.navigation_header);
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    switchToBlog();
//                    mDrawerLayout.closeDrawers();
//                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
            });
        }
    }
}
