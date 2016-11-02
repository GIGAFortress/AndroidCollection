package gigafortress.bluetoothsample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import java.util.Collections;
import java.util.Comparator;

import gigafortress.bluetoothsample.Unit.BluetoothDeviceContext;

/**
 * Created by Chenlij on 2016/11/1.
 */
public class DealMainActivity extends MainActivity {

    BroadcastReceiver broadcastReceiver;
    BluetoothAdapter.LeScanCallback leScanCallback;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitBluetoothReceiver();
        InitLeScanCallBack();
        IntentFilter mIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, mIntentFilter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void InitBluetoothReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                short RSSI = 0;
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        // 信号强度RSSI。
                        RSSI = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    }
                    blackBoard.appendInteractiveInfoAndShow("发现设备：" + device.getName());
                    BluetoothDeviceContext mBluetoothDeviceContext = new BluetoothDeviceContext(
                            device.getName() == null ? device.getAddress() : device.getName(),
                            device.getAddress(),
                            RSSI);

                    bluetoothDeviceContextList.add(mBluetoothDeviceContext);
                    Collections.sort(bluetoothDeviceContextList, new Comparator<BluetoothDeviceContext>() {
                        @Override
                        public int compare(BluetoothDeviceContext arg0, BluetoothDeviceContext arg1) {
                            return (arg1.RSSI - arg0.RSSI);
                        }
                    });
                    ovOAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void InitLeScanCallBack() {
        leScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int RSSI, byte[] scanRecord) {
//                for (int i = 0; i < bluetoothDeviceContextList.size(); i++) {
//                    if (bluetoothDevice.getAddress().equals(bluetoothDeviceContextList.get(i).address)) {
//                        synchronized (bluetoothDeviceContextList) {
//                            bluetoothDeviceContextList.remove(i);
//                            bluetoothDeviceContextList.add(new BluetoothDeviceContext(bluetoothDevice.getName(), bluetoothDevice.getAddress(), (short) RSSI));
//                        }
//                        return;
//                    }
//                }
                blackBoard.appendInteractiveInfoAndShow(bluetoothDevice.getName(), blackBoard.TIP);
//                synchronized (bluetoothDeviceContextList) {
//                    bluetoothDeviceContextList.add(new BluetoothDeviceContext(bluetoothDevice.getName(), bluetoothDevice.getAddress(), (short) RSSI));
//                }

            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    Boolean stopFlag;
    Handler handler = new Handler();

    Runnable taskBLE = new Runnable() {
        @Override
        public void run() {
            if (stopFlag) {
                bluetoothAdapter.stopLeScan(leScanCallback);
                blackBoard.appendInteractiveInfoAndShow("停止BLE搜索");
            } else {
                handler.postDelayed(taskBLE, 100);
            }
        }
    };

    Runnable taskStopDiscover = new Runnable() {
        @Override
        public void run() {
            if (stopFlag) {
                bluetoothAdapter.cancelDiscovery();
                blackBoard.appendInteractiveInfoAndShow("停止搜索");
            } else {
                handler.postDelayed(taskStopDiscover, 100);
            }
        }
    };

    @Override
    void startBLE() {
        if (bluetoothDeviceContextList != null) {
            bluetoothDeviceContextList.clear();
        }
        bluetoothAdapter.startLeScan(leScanCallback);
        stopFlag = false;
        handler.post(taskBLE);

    }

    @Override
    void startOnlyOnce() {
        if (bluetoothAdapter.isEnabled()) {
            if (bluetoothDeviceContextList != null) {
                bluetoothDeviceContextList.clear();
            }
            bluetoothAdapter.startDiscovery();
            blackBoard.appendInteractiveInfoAndShow("开始搜索");
            stopFlag = false;
            handler.post(taskStopDiscover);
            handler.post(taskStopDelay);

        } else {
            // 蓝牙未打开
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        }
    }

    Runnable taskStopDelay = new Runnable() {
        int i = 0;
        @Override
        public void run() {
            if (stopFlag) {
                i = 0;
            } else {
                handler.postDelayed(taskStopDelay, 100);
                i += 100;
                if (i >= 10000) {
                    stopFlag = true;
                }
            }
        }
    };

    @Override
    void stopScan() {
        stopFlag = true;

    }
}
