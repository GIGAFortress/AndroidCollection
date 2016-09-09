package com.example.chenlij.materialdesignpractice.example;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenlij.materialdesignpractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenlij on 2016/9/8.
 */
public class BottomTabActivity extends AppCompatActivity {

    private static String TAG = "BottomTabActivity";
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

        /*弃用*/
//        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);

        List<View> views = new ArrayList<View>();
        /*放置Fragment的Layout.xml文件*/
        int layoutResources[] = {
                R.layout.activity_bottom_tab_fragment1,
                R.layout.activity_bottom_tab_fragment2,
                R.layout.activity_bottom_tab_fragment3,
                R.layout.activity_bottom_tab_fragment1, };

        LayoutInflater lf = getLayoutInflater();

        /*更为简便的一种写法，Foreach枚举类型循环*/
        for (int layoutRes : layoutResources){
            views.add(lf.inflate(layoutRes, null));
        }

        MyViewPagerAdapter viewPageAdapater = new MyViewPagerAdapter(views, this);

        viewPager.setAdapter(viewPageAdapater);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(viewPageAdapater.getTabView(i));
            }
        }
//
//        viewPager.setCurrentItem(1);
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
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

    /*可以作为一个模板 MyFragmentPagerAdapter extends FragmentPagerAdapter*/
//    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
//
//        final int PAGE_COUNT = 4;
//        private Context context;
//
//        public View getTabView(int position) {
//            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
//            TextView tv = (TextView) v.findViewById(R.id.news_title);
//            tv.setText(tabTitles[position]);
//            ImageView img = (ImageView) v.findViewById(R.id.imageView);
//            img.setImageResource(imgResources[position]);
//            return v;
//        }
//
//        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
//            super(fm);
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            return PAGE_COUNT;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return PageFragment.newInstance(position + 1);
//        }
//
//        /*用来返回不知道是什么东西的标题，总之它并没有对TabLayout中的图标添加标题*/
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
//    }

    /*可以作为一个模板 PageFragment extends Fragment*/
//    public static class PageFragment extends Fragment{
//        public static final String ARG_PAGE = "ARG_PAGE";
//        private int layoutResources[] = {
//                R.layout.activity_bottom_tab_fragment1,
//                R.layout.activity_bottom_tab_fragment2,
//                R.layout.activity_bottom_tab_fragment3,
//                R.layout.activity_bottom_tab_fragment1, };
//
//        private int mPage;
//
//        public static PageFragment newInstance(int page) {
//            Bundle args = new Bundle();
//            args.putInt(ARG_PAGE, page);
//            PageFragment fragment = new PageFragment();
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            mPage = getArguments().getInt(ARG_PAGE);
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View view = inflater.inflate(layoutResources[mPage], container, false);
//
//            return view;
//        }
//    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private List<View> views;
        private Context context;

        String tabTitles[] = new String[]{"HCl","NaOH","Ag", "Au"};
        int imgResources[] = {R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4, };

        public MyViewPagerAdapter(List<View> views, Context context) {
            this.views = views;
            this.context = context;

          /*不能在里面进行循环填充Views，否则会报空指针，一种猜测可能是有个viewGroup填了个null导致的*/
//        LayoutInflater lf = getLayoutInflater();
//        for (int layoutRes : layoutResources){
//            views.add(lf.inflate(layoutRes, null));
//        }
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.news_title);
            tv.setText(tabTitles[position]);
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            img.setImageResource(imgResources[position]);
            return v;
        }
    }
}
