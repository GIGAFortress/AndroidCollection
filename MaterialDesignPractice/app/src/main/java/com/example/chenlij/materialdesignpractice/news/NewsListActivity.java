package com.example.chenlij.materialdesignpractice.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chenlij.materialdesignpractice.R;
import com.example.chenlij.materialdesignpractice.widget.DividerOffsetDecoration;
import com.example.chenlij.materialdesignpractice.widget.RefreshLayout;

/**
 * Created by Chenlij on 2016/8/29.
 */
public class NewsListActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
//        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(NewsListActivity.this, "onRefresh", 500).show();
//            }
//        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerOffsetDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
