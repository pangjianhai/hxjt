package cn.com.hxjt.core;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ExpandableListView mExpandableListView;

	private int[] arrows;

	private List<String> tasks;

	private List<String> myTs;
	private List<String> fpyTs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initPart();
	}

	private void initData() {
		arrows = new int[] { R.drawable.arrow_bottom, R.drawable.arrow_bottom };
		tasks = new ArrayList();
		tasks.add("我的任务");
		tasks.add("分配任务");

		myTs = new ArrayList();
		myTs.add("已经完成的");
		myTs.add("未完成的(2)");
		myTs.add("将要开始的(12)");

		fpyTs = new ArrayList();
		fpyTs.add("已经完成的(8)");
		fpyTs.add("未完成的(2)");
		fpyTs.add("将要开始的(12)");
	}

	/**
	 * 
	 * 
	 * @user:pang
	 * @data:2015年7月19日
	 * @todo:初始化
	 * @return:void
	 */
	private void initPart() {

		// 准备好数据
		initData();

		mExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
		// 去掉箭头
		mExpandableListView.setGroupIndicator(null);
		// 设置adapter，找adapter要数据
		mExpandableListView.setAdapter(new MyExpandableListAdapter());

		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String title = "";
				if (groupPosition == 0) {
					title = myTs.get(childPosition);
				} else {
					title = fpyTs.get(childPosition);
				}
				Toast.makeText(getApplicationContext(), title,
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private class MyExpandableListAdapter extends BaseExpandableListAdapter {

		/**
		 * 获取指定组的长度，即指定组有多少个子列表项
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			int total = 0;
			if (groupPosition == 0) {
				total = myTs.size();
			} else {
				total = fpyTs.size();
			}
			return total;
		}

		/**
		 * 返回指定组位置，子列表项的位置
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * 获得指定组位置，指定子列表项处，子列表项中的数据
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String title = "";
			if (groupPosition == 0) {
				title = myTs.get(childPosition);
			} else {
				title = fpyTs.get(childPosition);
			}
			return title;
		}

		/**
		 * 返回指定组位置，指定子列表项的外观
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.type_tasks, null);
			TextView task_title = (TextView) view.findViewById(R.id.task_title);
			task_title.setText(getChild(groupPosition, childPosition)
					.toString());
			return view;
		}

		/**
		 * 获得组的个数
		 */
		@Override
		public int getGroupCount() {
			return tasks.size();
		}

		/**
		 * 返回指定组位置
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * 返回指定组列表项中的数据
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return tasks.get(groupPosition);
		}

		/**
		 * 返回组列表项的外观
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.types, null);
			TextView group_title = (TextView) view
					.findViewById(R.id.group_title);
			group_title.setText(getGroup(groupPosition).toString());
			group_title.setTextSize(20);
			ImageView group_img = (ImageView) view.findViewById(R.id.group_img);
			if (isExpanded) {
				group_img.setImageResource(R.drawable.arrow_top);// 箭头向上
			} else {// 折叠
				group_img.setImageResource(R.drawable.arrow_bottom);// 箭头向下
			}

			return view;
		}

		/**
		 * 判断是否稳定
		 */
		@Override
		public boolean hasStableIds() {
			return false;
		}

		/**
		 * 当选择子节点时调用
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

}
