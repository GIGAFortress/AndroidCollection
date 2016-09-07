package com.example.chenlij.materialdesignpractice.news;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenlij.materialdesignpractice.ExampleFragment;
import com.example.chenlij.materialdesignpractice.R;
import com.example.chenlij.materialdesignpractice.widget.DividerOffsetDecoration;
import com.example.chenlij.materialdesignpractice.widget.PullToRefreshLayout;
import com.example.chenlij.materialdesignpractice.widget.RefreshLayout;

import org.w3c.dom.Text;

/**
 * Created by Chenlij on 2016/8/29.
 */
public class NewsListActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Toolbar toolbar;
    private Handler mHandler;
    private Runnable mRunnable;
    private Adapter mAdapter;
    private String[] mString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mString = new String[]{"Alpha", "Beta", "Charli", "Delta"};
        initRunnable();
        initToolBar();
        initRefreshView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerOffsetDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MyAdapter(mString);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initRunnable() {
        mHandler = new Handler();
        mRunnable = new Runnable(){

            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        };
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("最新");
        setSupportActionBar(toolbar);   //Mainfest中没有设置AppTheme也不会报错
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            /*设置后ToolBar返回键作用启动*/
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initRefreshView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        assert null != refreshLayout;
        
        /*最多可以设置4种类的颜色*/
        refreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refreshLayout.setRefreshing(true);
        mHandler.postDelayed(mRunnable, 3000);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(mRunnable, 3000);
                Toast.makeText(NewsListActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
            }
        });
//        refreshLayout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                Toast.makeText(NewsListActivity.this, "setOnLoadListenr", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /*自定义Adapter，使用item_view.xml的样式*/
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private String[] mString;

        /*传入String参数*/
        public MyAdapter(String[] mString) {
            this.mString = mString;
        }

        /*创建自定义的ViewHolder类，要与item_view.xml中的控件对应上，每个条目都将生成个此ViewHolder*/
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView mImageView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.news_title);  //此处要注意要使用传入的View类型参数来寻找ID，不然会是空指针
                mImageView = (ImageView) itemView.findViewById(R.id.news_image);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //创建ViewHolder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //绑定数据
            holder.mTextView.setText(mString[position]);
        }

        @Override
        public int getItemCount() {
            return mString.length;
        }
    }
}
