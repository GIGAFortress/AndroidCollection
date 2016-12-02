package gigafortress.asynctaskjson;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gigafortress.asynctaskjson.Adapter.NewsAdapter;
import gigafortress.asynctaskjson.Bean.NewsBean;

/**
 * Created by Chenlij on 2016/12/1.
 */
public class DealActivity extends MainActivity {

    public static String url1 = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NewsAsyncTask().execute(url1);
    }

    /**
     * execute传入参数url1，对应的是AsyncTask的<String>参数
     * 实现网络的异步访问
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        /**
         * 启动一个线程执行该任务
         * @param strings 通过execute方法传入的参数，也就是url1
         * @return
         */
        @Override
        protected List<NewsBean> doInBackground(String... strings) {
            //此处传入的String... strings 是个String类型的数组，由于只有一个String类的url1传入，所以填strings[0]也就是url1这个具体数值
            return getJsonData(strings[0]);
        }

        /**
         * doInBackGround结束之后执行该任务，
         * @param newsBeen doInBackGround函数返回的数据，也就是获取到的JSon数据
         */
        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            super.onPostExecute(newsBeen);
            NewsAdapter newsAdapter = new NewsAdapter(DealActivity.this, newsBeen);
            listview1.setAdapter(newsAdapter);
        }
    }

    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeanList = new ArrayList<>();

        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            NewsBean newsBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean = new NewsBean();
                    newsBean.newsTitle = jsonObject.getString("name");
                    newsBean.newsIconUrl = jsonObject.getString("picSmall");
                    newsBean.newsContent = jsonObject.getString("description");
                    newsBeanList.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    /**
     * 通过InputStream解析网页返回的数据
     * @param is
     * @return
     */
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        String line = "";
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
