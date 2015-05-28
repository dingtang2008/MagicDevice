package com.elvis.magicdevice.MainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elvis.magicdevice.BuildPropTool;
import com.elvis.magicdevice.Content.Utils;
import com.elvis.magicdevice.MainScreen.ui.BootstrapButton;
import com.elvis.magicdevice.MainScreen.ui.PinnedHeaderExpandableListView;
import com.elvis.magicdevice.MainScreen.ui.StickyLayout;
import com.elvis.magicdevice.R;

public class MainActivity extends Activity implements
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        PinnedHeaderExpandableListView.OnHeaderUpdateListener, StickyLayout.OnGiveUpTouchEventListener {
    private PinnedHeaderExpandableListView expandableListView;
    private StickyLayout stickyLayout;
    private ArrayList<Group> groupList;
    private ArrayList<List<DeviceItem>> childList;

    private GroupExpandableListAdapter adapter;
    private String[] groupName = new String[]{"设备信息", "模拟位置", "备份"};
    public static int deviceinfo = 0;
    public static int mocklocation = 1;
    public static int backup = 2;
    public static final Properties prop = BuildPropTool.getPropMap();


    AlertDialog.Builder builder;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
        stickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
        initData();


        adapter = new GroupExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);

        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        stickyLayout.setOnGiveUpTouchEventListener(this);

    }

    /**
     * InitData
     */
    void initData() {
        groupList = new ArrayList<Group>();
        Group group = null;
        for (int i = 0; i < 3; i++) {
            group = new Group();
            group.setTitle(groupName[i]);
            groupList.add(group);
        }

        childList = new ArrayList<List<DeviceItem>>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<DeviceItem> childTemp;
            if (i == deviceinfo) {
                childTemp = new ArrayList<DeviceItem>();
                for (String[] item : Utils.info) {
                    DeviceItem deviceItem = new DeviceItem();
                    deviceItem.setName(item[0]);
                    deviceItem.setDeviceInfo(prop.getProperty(item[1]));

                    childTemp.add(deviceItem);
                }
            } else if (i == mocklocation) {
                childTemp = new ArrayList<DeviceItem>();
                for (int j = 0; j < 8; j++) {
                    DeviceItem deviceItem = new DeviceItem();
                    deviceItem.setName("ff-" + j);
                    deviceItem.setDeviceInfo("sh-" + j);

                    childTemp.add(deviceItem);
                }
            } else {//backup
                childTemp = new ArrayList<DeviceItem>();
                for (int j = 0; j < 2; j++) {
                    DeviceItem deviceItem = new DeviceItem();
                    deviceItem.setName("hh-" + j);
                    deviceItem.setDeviceInfo("sh-" + j);

                    childTemp.add(deviceItem);
                }
            }
            childList.add(childTemp);
        }

    }

    /**
     * 数据源
     *
     * @author Administrator
     */
    class GroupExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;

        public GroupExpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
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
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group, null);
                groupHolder.textView = (TextView) convertView
                        .findViewById(R.id.group);
                groupHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(((Group) getGroup(groupPosition))
                    .getTitle());
            if (isExpanded)// ture is Expanded or false is not isExpanded
                groupHolder.imageView.setImageResource(R.drawable.expanded);
            else
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
           // if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.child, null);

                childHolder.textName = (TextView) convertView
                        .findViewById(R.id.name);
                childHolder.textAddress = (EditText) convertView
                        .findViewById(R.id.deviceinfo);
                childHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                final int[] tag = new int[]{groupPosition, childPosition};
                final BootstrapButton button = (BootstrapButton) convertView
                        .findViewById(R.id.button1);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "clicked GroupPos=" + tag[0] + "ChinldPos" + tag[1], Toast.LENGTH_SHORT).show();
                        editText = new EditText(MainActivity.this);
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("自定义").setView(editText);
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                ((DeviceItem) getChild(groupPosition,
                                        childPosition)).setDeviceInfo(editText.getText().toString());
                            }
                        }).show();
                    }
                });

                convertView.setTag(childHolder);
           /*} else {
                childHolder = (ChildHolder) convertView.getTag();
            }*/
            childHolder.textName.setText(((DeviceItem) getChild(groupPosition,
                    childPosition)).getName());
//            childHolder.textAge.setText(String.valueOf(((DeviceItem) getChild(
//                    groupPosition, childPosition)).getAge()));
            childHolder.textAddress.setText(((DeviceItem) getChild(groupPosition,
                    childPosition)).getDeviceinfo());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v,
                                int groupPosition, final long id) {

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(MainActivity.this,
                childList.get(groupPosition).get(childPosition).getName(), Toast.LENGTH_SHORT).show();

        return false;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textName;
        EditText textAddress;
        ImageView imageView;
    }

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (expandableListView.getFirstVisiblePosition() == 0) {
            View view = expandableListView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

}