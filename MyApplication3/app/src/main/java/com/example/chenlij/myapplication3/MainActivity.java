package com.example.chenlij.myapplication3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView textViewC21;
    public TextView textViewC22;
    public TextView textViewC11;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private List<BluetoothDeviceContext> discoveryDevices = new ArrayList<BluetoothDeviceContext>();

    private final BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            short RSSI = 0;
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    RSSI = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                }
                BluetoothDeviceContext btContext = new BluetoothDeviceContext
                        (device.getName() == null ? device.getAddress() : device.getName(), device.getAddress(), RSSI);
                discoveryDevices.add(btContext);
                textViewC21.append("\n" + btContext.name + "\n" + btContext.address + "\nRSSI=" + btContext.RSSI);
                textViewC21.append("\n");
                Log.d("B","\n" + btContext.name + "\n" + btContext.address + "\nRSSI=" + btContext.RSSI);
            }
        }
    };

    public void startDiscovery(View view){
        textViewC21.setText("");
        if(bluetoothAdapter.isEnabled()){
            if(discoveryDevices != null){
                discoveryDevices.clear();
            }
            Toast.makeText(MainActivity.this, "startDiscovery", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.startDiscovery();
            new Thread( new Runnable(){
                @Override
                public void run() {
                    try{
                        Thread.sleep(3000);
                    } catch(InterruptedException e){

                    } finally{
                        bluetoothAdapter.cancelDiscovery();

                        /*此处若不适用UI线程来处理会报错：该线程无法对UI线程进行操作*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sortBluetoothDevice();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void sortBluetoothDevice() {
        textViewC22.setText("");

        /*List排序方法Collections.sort*/
        Collections.sort(discoveryDevices, new Comparator<BluetoothDeviceContext>() {
            @Override
            public int compare(BluetoothDeviceContext lhs, BluetoothDeviceContext rhs) {
                return (rhs.RSSI - lhs.RSSI);
            }
        });
        final String[] bluetoothNames = new String[discoveryDevices.size()];
        for(BluetoothDeviceContext device : discoveryDevices) {
            textViewC22.append("\n" + device.name + "\n" + device.address + "\nRSSI=" + device.RSSI + "\n");
        }
    }

    private class BluetoothDeviceContext{
        public String name = "";
        public String address = "";
        public short RSSI = 0;

        BluetoothDeviceContext(String name, String address, short RSSI) {
            this.name = name;
            this.address = address;
            this.RSSI = RSSI;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializedView();
        registerBluetoothListener();
    }

    private void registerBluetoothListener() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(discoveryReceiver, filter);
    }

    private void initializedView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
//        View view1 = findViewById(R.layout.content_1);
//        View view2 = findViewById(R.layout.content_2);
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.content_1, null);
        View view2 = lf.inflate(R.layout.content_2, null);
        textViewC21 = (TextView) view2.findViewById(R.id.textViewC21);
        textViewC22 = (TextView) view2.findViewById(R.id.textViewC22);
        textViewC11 = (TextView) view1.findViewById(R.id.textViewC11);
        final List<View> listviews = new ArrayList<View>();
        listviews.add(view1);
        listviews.add(view2);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(listviews.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(listviews.get(position));
                return listviews.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(discoveryReceiver);
    }

    public void showToast(View view){
        Toast.makeText(this, "SHOWTOAST", Toast.LENGTH_SHORT).show();
        textViewC11.setText("Test?!");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "manage", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
