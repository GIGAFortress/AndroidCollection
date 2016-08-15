package com.example.chenlij.weboviewswitcheffect;

import android.app.Notification;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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
    private PullToRefreshListView mPullToRefresh;

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
        Initview2();
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
        int[] pics = {R.drawable.pic1, R.drawable.pic2,
            R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic6, R.drawable.pic7 };
        String[] titles = {"新浪新闻", "中国日报", "今日头条", "环球时报", "慕课网", "福州时事", "福州ing"};
        String[] contents = {"http://t.cn/Rt0j5Gt", "http://t.cn/RUuz4wV", "欢迎您关注今日头条官方微博，更多精彩资讯",
                "据英国路透社22日报道，土耳其总理达武特奥卢", "亲，欢迎关注慕课网官方微博", "福州本地", "福州ing"};
        for (int i = 0; i < contents.length; i++) {
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
//        setListViewHeightBasedOnChildren(listView);
    }

    private void Initview2() {
        mPullToRefresh = (PullToRefreshListView) view2.findViewById(R.id.pull_refresh_list);
        ListView actualListView = mPullToRefresh.getRefreshableView();
        LinkedList<String> mListItems = new LinkedList<String>();
        String[] mStrings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",};
        mListItems.addAll(Arrays.asList(mStrings));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.simpleitem, mListItems);
        actualListView.setAdapter(mAdapter);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
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
//            Toast.makeText(MainActivity.this, "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}