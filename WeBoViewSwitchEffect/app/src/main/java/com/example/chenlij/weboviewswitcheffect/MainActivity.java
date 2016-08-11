package com.example.chenlij.weboviewswitcheffect;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private int bmpWidth;
    private int offset;
    private List<View> views;
    private ViewPager viewPager;
    private View view1;
    private View view2;
    private View view3;
    private int currIndex;
    private ListView listView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitImageView();
        InitViewPager();
    }

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);

//        原版这里是获取图片的宽度来控制，但不方便灵活
//        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        bmpWidth = screenW / 3;
        offset = (screenW / 3 - bmpWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

        /*设置imageView控件的宽度*/
        imageView.getLayoutParams().width = bmpWidth;
        imageView.setImageMatrix(matrix);
    }

    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        views = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater();
        view1 = lf.inflate(R.layout.view1, null);
        view2 = lf.inflate(R.layout.view2, null);
        view3 = lf.inflate(R.layout.view3, null);
        Initview1();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }

    private void Initview1() {
        listView = (ListView) view1.findViewById(R.id.listView1);
        dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        int[] pics = {R.drawable.pic1, R.drawable.pic2 };
        String[] titles = {"新浪新闻", "中国日报"};
        String[] contents = {"http://t.cn/Rt0j5Gt", "http://t.cn/RUuz4wV"};
        for (int i = 0; i < pics.length; i++) {
            map = new HashMap<String, Object>();
            map.put("pic", pics[i]);
            map.put("title1", titles[i]);
            map.put("content1", contents[i]);
            Log.e("TAG","i="+i);
            dataList.add(map);
        }
        String[] from = {"pic", "title1", "content1"};
        int[] to = {R.id.pic, R.id.title1, R.id.content1};
        listAdapter = new SimpleAdapter(this, dataList, R.layout.itemstyle, from, to);
        listView.setAdapter(listAdapter);
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public MyViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return 3;
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
    }

    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpWidth;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = position;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(MainActivity.this, "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}