package com.example.chenlij.materialdesignpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenlij.materialdesignpractice.example.AppBarDetailActivity;
import com.example.chenlij.materialdesignpractice.example.BottomTabActivity;
import com.example.chenlij.materialdesignpractice.example.CardViewActivity;
import com.example.chenlij.materialdesignpractice.news.NewsListActivity;
import com.example.chenlij.materialdesignpractice.widget.DividerItemDecoration;
import com.example.chenlij.materialdesignpractice.widget.RecyclerItemClickListener;

/**
 * 通过侧滑菜单选择控件切换至该页面（FragMent类型）
 * Created by Chenlij on 2016/8/29.
 */
public class ExampleFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] myDataset;
    private MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*Fragment类型的页面使用inflater来装载*/
        View view = inflater.inflate(R.layout.fragment_example, null);

        /*寻找该fragment上的控件的时候注意标明view*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);    //Adapter无法修改RecyclerView的尺寸
        mLayoutManager = new LinearLayoutManager(getActivity());    //创建一个垂直属性的LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);     //设置RecyclerView将会使用的Layout
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST)); //为RecyclerView添加个ItemDecoration
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myDataset = new String[]{"RecycleView",
                "TextInputLayout", "CardView", "AppBar & TabLayout","Bottom Tab","Test"
        };
        mAdapter = new MyAdapter(getActivity(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    /*此处作RecyclerView的点击监听*/
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            Intent intent = null;
            switch (position) {
                case 0:
                    intent = new Intent(getActivity(), NewsListActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getActivity(), CardViewActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getActivity(), AppBarDetailActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(getActivity(), BottomTabActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private String[] mDataset;
        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        /*自定义类型ViewHolder*/
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ImageView mImageView;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.news_title);     //在onCreateViewHolder中使用到的样式中的控件
                mImageView = (ImageView) v.findViewById(R.id.news_image);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context, String[] myDataset) {
            mDataset = myDataset;
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            /*固定步骤*/
            //创建一个View，使用的Item资源
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTextView.setText(mDataset[position]);
            holder.mImageView.setImageResource(R.drawable.heibaixiong);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}
