package gigafortress.asynctaskjson.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chenlij on 2016/12/2.
 */
public class ImageLoader {

    private ImageView imageView;
    private String mUrl;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //作用： 只有当传入的url与imageView被设置的Tag中的url相等的时候才去设置图片
            if(imageView.getTag().equals(mUrl))
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public void showImageByThread(ImageView imageView, final String url) {
        this.imageView= imageView;
        mUrl = url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromURL(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                mhandler.sendMessage(message);
            }
        }.start();
    }

    private Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(httpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            httpURLConnection.disconnect();
            is.close();
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
