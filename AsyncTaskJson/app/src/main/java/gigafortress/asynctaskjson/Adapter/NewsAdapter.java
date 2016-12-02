package gigafortress.asynctaskjson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import gigafortress.asynctaskjson.Bean.NewsBean;
import gigafortress.asynctaskjson.R;
import gigafortress.asynctaskjson.Util.ImageLoader;

/**
 * Created by Chenlij on 2016/12/2.
 */
public class NewsAdapter extends BaseAdapter {

    Context context;
    List<NewsBean> mList;
    LayoutInflater layoutInflater;

    public NewsAdapter(Context context, List<NewsBean> mList) {
        this.mList = mList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_layout, null);
            viewHolder.newsIcon = (ImageView) view.findViewById(R.id.imageview1);
            viewHolder.newsTitle = (TextView)view.findViewById(R.id.textview1);
            viewHolder.newsContent = (TextView)view.findViewById(R.id.textview2);
            view.setTag(viewHolder);    //此处如果不作setTag，那之后如果有滑动的话会导致下面getTag获取不到东西从而导致空指针
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String url =  mList.get(i).newsIconUrl;
        viewHolder.newsIcon.setImageResource(R.mipmap.ic_launcher);
        viewHolder.newsIcon.setTag(url);    //设置url标签，在ImageLoader中会调用到用来确保缓存的图片不会错乱
        new ImageLoader().showImageByThread(viewHolder.newsIcon, url);
        viewHolder.newsTitle.setText(mList.get(i).newsTitle);
        viewHolder.newsContent.setText(mList.get(i).newsContent);
        return view;
    }

    public class ViewHolder {
        ImageView newsIcon;
        TextView newsTitle;
        TextView newsContent;
    }
}
