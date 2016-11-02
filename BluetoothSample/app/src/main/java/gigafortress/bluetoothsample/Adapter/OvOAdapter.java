package gigafortress.bluetoothsample.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gigafortress.bluetoothsample.R;
import gigafortress.bluetoothsample.Unit.BluetoothDeviceContext;

/**
 * Created by Chenlij on 2016/11/1.
 */
public class OvOAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<BluetoothDeviceContext> bluetoothDeviceContext;

    public OvOAdapter(Context context, List<BluetoothDeviceContext> bluetoothDeviceContext) {
        this.mInflater = LayoutInflater.from(context);
        this.bluetoothDeviceContext = bluetoothDeviceContext;
    }

    @Override
    public int getCount() {
        return bluetoothDeviceContext.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            myViewHolder = new MyViewHolder();
            myViewHolder.tx_DeviceName = (TextView) convertView.findViewById(R.id.tx_DeviceName);
            myViewHolder.tx_MacAddress = (TextView) convertView.findViewById(R.id.tx_MacAddress);
            myViewHolder.tx_RSSI = (TextView) convertView.findViewById(R.id.tx_RSSI);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.tx_DeviceName.setText(bluetoothDeviceContext.get(position).name);
        myViewHolder.tx_MacAddress.setText(bluetoothDeviceContext.get(position).address);
        myViewHolder.tx_RSSI.setText("" + bluetoothDeviceContext.get(position).RSSI);

        return convertView;
    }

    public class MyViewHolder {
        TextView tx_DeviceName;
        TextView tx_MacAddress;
        TextView tx_RSSI;
    }
}
