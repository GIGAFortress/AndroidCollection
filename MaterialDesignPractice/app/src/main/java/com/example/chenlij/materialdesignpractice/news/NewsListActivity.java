package com.example.chenlij.materialdesignpractice.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.chenlij.materialdesignpractice.R;
import com.example.chenlij.materialdesignpractice.widget.DividerOffsetDecoration;
import com.example.chenlij.materialdesignpractice.widget.PullToRefreshLayout;
import com.example.chenlij.materialdesignpractice.widget.RefreshLayout;

/**
 * Created by Chenlij on 2016/8/29.
 */
public class NewsListActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initToolBar();
        initRefreshView();
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerOffsetDecoration());
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
//        assert null == refreshLayout;
        refreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 6000);
                Toast.makeText(NewsListActivity.this, "onRefresh", 500).show();
            }
        });
//        refreshLayout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                Toast.makeText(NewsListActivity.this, "setOnLoadListenr", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
