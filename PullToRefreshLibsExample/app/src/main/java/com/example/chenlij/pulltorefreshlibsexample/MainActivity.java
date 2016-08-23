package com.example.chenlij.pulltorefreshlibsexample;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView mPullRefreshListView;
    private LinkedList<Map<String, Object>> mListItems;
    private SimpleAdapter mAdapter;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<ScanResult> listResult;
    private ScanResult mScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(MainActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });
        ListView actualListView = mPullRefreshListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(actualListView);

        mListItems = new LinkedList<Map<String, Object>>();

        mAdapter = new SimpleAdapter(this, mListItems, R.layout.wifi_item, new String[]{"SSID", "LEVEL"}, new int[]{R.id.SSID1, R.id.level1});
        actualListView.setAdapter(mAdapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                mWifiManager.startScan();
                listResult = mWifiManager.getScanResults();
                if (listResult != null) {
                    Collections.sort(listResult, new Comparator<ScanResult>() {
                        @Override
                        public int compare(ScanResult arg0, ScanResult arg1) {
                            return (arg1.level - arg0.level);
                        }
                    });
                    mListItems.removeAll(mListItems);
                    for (int i = 0; i < listResult.size(); i++) {
                        mScanResult = listResult.get(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("SSID", mScanResult.SSID);
                        map.put("LEVEL", mScanResult.level);
                        mListItems.add(map);
//                        mListItems.addFirst(mScanResult.SSID);
                    }
                }

            } catch (Exception e) {
            }
            return new String[]{"Hello"};
        }

        @Override
        protected void onPostExecute(String[] result) {
//            mListItems.addFirst("Added after refresh...");
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

}
