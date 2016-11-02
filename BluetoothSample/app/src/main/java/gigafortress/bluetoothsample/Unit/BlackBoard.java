package gigafortress.bluetoothsample.Unit;

import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Chenlij on 2016/10/9.
 */
public class BlackBoard {
    private final int ROW = 20;    //显示最多行数
    public final int NORMAL = 0;
    public final int ERROR = 1;
    public final int TIP = 2;

    TextView textView;
    ScrollView scrollView;
    Handler mHandler = new Handler();
    StringBuffer deviceInteraction = new StringBuffer();
    private String TAG = "BlackBoard";
    private int row = 0;

    public BlackBoard(TextView textView, ScrollView scrollView) {
        this.textView = textView;
        this.scrollView = scrollView;
    }

    public void appendInteractiveInfoAndShow(final String string) {
        String newstring = "<font color='black'>" + string + "</font>";
        if (row > ROW) {
            deviceInteraction.delete(0, deviceInteraction.indexOf("<br>") + 4);
            row--;
        }
        deviceInteraction.append(newstring).append("<br>");
        row++;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(Html.fromHtml(deviceInteraction.toString()));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 50);
            }
        });

    }

    public void appendInteractiveInfoAndShow(final String string, final int messageTag) {
        String newstring = "<font color='black'>" + string + "</font>";
        if (row > ROW) {
            deviceInteraction.delete(0, deviceInteraction.indexOf("<br>") + 4);
            row--;
        }
        switch (messageTag) {
            case NORMAL:
                newstring = "<font color='black'>" + string + "</font>";
                break;
            case ERROR:
                newstring = "<font color='#D1343B'>" + string + "</font>";    //红色
                break;
            case TIP:
                newstring = "<font color='#1E88E5'>" + string + "</font>";    //蓝色
                break;
            default:
                break;
        }
        deviceInteraction.append(newstring).append("<br>");
        row++;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(Html.fromHtml(deviceInteraction.toString()));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 50);
            }
        });
    }

    public void clear() {
        deviceInteraction.delete(0, deviceInteraction.length());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(deviceInteraction);
            }
        });
    }
}