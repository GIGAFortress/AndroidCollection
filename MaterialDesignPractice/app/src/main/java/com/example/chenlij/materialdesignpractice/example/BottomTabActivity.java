package com.example.chenlij.materialdesignpractice.example;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenlij.materialdesignpractice.R;

/**
 * Created by Chenlij on 2016/9/8.
 */
public class BottomTabActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*AppCompatActivity有两种onCreate，要选择使用只有一个参数的onCreate*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        initToolBar();
        initTabViewPager();
    }

    private void initTabViewPager() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        MyFragmentPagerAdapter pagerAdapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);


        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }
//
//        viewPager.setCurrentItem(1);
//        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_buttom_tab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 4;
        private Context context;
        private String tabTitles[] = new String[]{"Ag", "Au", "Cu", "Ca"};
        private int imgResources[] = {R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4 };

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.news_title);
            tv.setText(tabTitles[position]);
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            img.setImageResource(imgResources[position]);
            //img.setImageResource(imageResId[position]);
            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*这是可选的非必要的*/
            return tabTitles[position];
        }
    }

    public static class PageFragment extends Fragment{

    }
}
