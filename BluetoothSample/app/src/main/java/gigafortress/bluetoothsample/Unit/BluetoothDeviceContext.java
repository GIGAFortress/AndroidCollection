package gigafortress.bluetoothsample.Unit;

/**
 * Created by Chenlij on 2016/9/18.
 */
public class BluetoothDeviceContext {
    public String name = "";
    public String address = "";
    public short RSSI = 0;

    public BluetoothDeviceContext(String name, String address, short RSSI) {
        this.name = name;
        this.address = address;
        this.RSSI = RSSI;
    }
}
