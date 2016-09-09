package com.example.chenlij.materialdesignpractice;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.chenlij.materialdesignpractice.book.BooksFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        getSupportActionBar().hide();       //隐藏AppCompatActivity的标题，默认主题下生效，在MainFest中使用AppTheme或其子类的主题的话会导致空指针
        setSupportActionBar(mToolbar);    //没有在MainFest下设置AppTheme的会报空指针的错误


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        setupDrawerContent(mNavigationView);    //设置Menu的点击事件
        setUpProfileImage();    //设置Menu中的头像
    }
    private void setUpProfileImage() {
        View headerView=  mNavigationView.inflateHeaderView(R.layout.navigation_header);    //NavigationView方法inflateHeaderView填充一个头部
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*点击头像跳往博客页面*/
//                    switchToBlog();
//                    mDrawerLayout.closeDrawers();
//                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
            });
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {

        /*对NavigationView中嵌套的Menu的Item设置监听*/
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            case R.id.navigation_item_book:
                                switchToBook();
                                break;
                            case R.id.navigation_item_example:
                                switchToExample();
                                break;
                            case R.id.navigation_item_blog:
                                switchToBlog();
                                break;
                            case R.id.navigation_item_about:
                                switchToAbout();
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void switchToBook() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BooksFragment()).commit();
        mToolbar.setTitle(R.string.navigation_book);
    }

    private void switchToExample() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ExampleFragment()).commit();
        mToolbar.setTitle(R.string.navigation_example);
    }

    private void switchToBlog() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BlogFragment()).commit();
        mToolbar.setTitle(R.string.navigation_my_blog);
    }

    private void switchToAbout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
        mToolbar.setTitle(R.string.navigation_about);
    }

}
