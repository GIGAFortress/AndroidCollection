package gigafortress.bluetoothsample;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gigafortress.bluetoothsample.Adapter.OvOAdapter;
import gigafortress.bluetoothsample.Unit.BlackBoard;
import gigafortress.bluetoothsample.Unit.BluetoothDeviceContext;

public abstract class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<BluetoothDeviceContext> bluetoothDeviceContextList;
    OvOAdapter ovOAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitViewID();
        InitListView();
        InitButtonOnClick();
    }

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    Button btn_BLEMode, btn_OnlyOnce, btn_StopScan;
    ScrollView scrollview;
    TextView tx_info;
    BlackBoard blackBoard;
    ListView listView;

    private void InitViewID() {
        btn_BLEMode = $(R.id.btn_BLEmode);
        btn_OnlyOnce = $(R.id.btn_OnlyOnce);
        btn_StopScan = $(R.id.btn_StopScan);
        scrollview = $(R.id.scrollview);
        tx_info = $(R.id.tx_Info);
        listView = $(R.id.listview);
        blackBoard = new BlackBoard(tx_info, scrollview);
    }

    private void InitListView() {
        bluetoothDeviceContextList = new ArrayList<BluetoothDeviceContext>();
        ovOAdapter = new OvOAdapter(this, bluetoothDeviceContextList);
        listView.setAdapter(ovOAdapter);
    }

    private void InitButtonOnClick() {
        btn_BLEMode.setOnClickListener(this);
        btn_OnlyOnce.setOnClickListener(this);
        btn_StopScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_BLEmode:
                startBLE();
                break;
            case R.id.btn_OnlyOnce:
                startOnlyOnce();
                break;
            case R.id.btn_StopScan:
                stopScan();
                break;
        }
    }

    abstract void startBLE();

    abstract void startOnlyOnce();

    abstract void stopScan();
}
