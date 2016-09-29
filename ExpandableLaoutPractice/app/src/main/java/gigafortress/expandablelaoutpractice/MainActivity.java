package gigafortress.expandablelaoutpractice;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gigafortress.expandablelaoutpractice.AnimatedExpandableListView.AnimatedExpandableListAdapter;

public class MainActivity extends AppCompatActivity {

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    AnimatedExpandableListView animatedExpandableListView;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<GroupItem> items = new ArrayList<GroupItem>();

        // Populate our list with groups and it's children
        for (int i = 1; i < 10; i++) {
            GroupItem item = new GroupItem();

            item.title = "Group " + i;

            for (int j = 0; j < 4; j++) {
                ChildItem child = new ChildItem();
                child.title = "Awesome item " + j;
                child.hint = "Too awesome";

                item.items.add(child);
            }

            items.add(item);
        }


        ExampleAdapter exampleAdapter = new ExampleAdapter(this);
        exampleAdapter.setData(items);


        animatedExpandableListView = $(R.id.expandableListView);
        animatedExpandableListView.setAdapter(exampleAdapter);

        animatedExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                if(animatedExpandableListView.isGroupExpanded(groupPosition)){
                    animatedExpandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    animatedExpandableListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        //让展开的全部折叠起来
        btn1 = $(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 9; i++){
                    if(animatedExpandableListView.isGroupExpanded(i)) {
                        animatedExpandableListView.collapseGroupWithAnimation(i);
                    }
                }
            }
        });

    }

    private class ExampleAdapter extends AnimatedExpandableListAdapter{

        private LayoutInflater inflater;

        private List<GroupItem> items;

        //自定义构造函数
        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        //设置数据源
        public void setData(List<GroupItem> items) {
            this.items = items;
        }



        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            holder.hint.setText(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    private class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }
    private static class ChildItem {
        String title;
        String hint;
    }
    private static class ChildHolder {
        TextView title;
        TextView hint;
    }
    private static class GroupHolder {
        TextView title;
    }
}
