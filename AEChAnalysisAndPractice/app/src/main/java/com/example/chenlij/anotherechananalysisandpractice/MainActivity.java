package com.example.chenlij.anotherechananalysisandpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.chenlij.anotherechananalysisandpractice.common.AbsActivity;

public class MainActivity extends AbsActivity {


    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = $(R.id.recycler_view);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mAdapter = new PageListAdapter();
        mListView.setHasFixedSize(false);
        mListView.setAdapter(mAdapter);
    }
}
