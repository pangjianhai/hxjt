package cn.com.hxjt.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
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
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskEntity;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 首页
 * @author pang
 *
 */
public class HomeActivity extends BaseActivity {

	private ExpandableListView mExpandableListView;

	/**
	 * 箭头
	 */
	private int[] arrows;

	/**
	 * 两个分类
	 */
	private List<String> tasks;

	/**
	 * 每类下面的任务属性和数量
	 */
	private List<String> myTs;
	private List<String> fpyTs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initPart();
		initAlis();
	}

	/**
	 * @user:pang
	 * @data:2015年7月19日
	 * @todo:初始化数据
	 * @return:void
	 */
	private void initData1() {
		arrows = new int[] { R.drawable.arrow_bottom, R.drawable.arrow_bottom };
		tasks = new ArrayList();
		tasks.add("我的任务");
		tasks.add("分配任务");

		myTs = new ArrayList();
		myTs.add("已经完成(1)");
		myTs.add("未完成(2)");
		myTs.add("将要开始的(12)");

		fpyTs = new ArrayList();
		fpyTs.add("已经完成(4)");
		fpyTs.add("未完成(8)");
		fpyTs.add("将要开始的(6)");
	}

	private void initPart() {

		initData();

		mExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
		mExpandableListView.setGroupIndicator(null);
		mExpandableListView.setAdapter(new MyExpandableListAdapter());

		// mExpandableListView.setOnChildClickListener(new
		// OnChildClickListener() {
		//
		// @Override
		// public boolean onChildClick(ExpandableListView parent, View v,
		// int groupPosition, int childPosition, long id) {
		// String title = "";
		// if (groupPosition == 0) {
		// title = myTs.get(childPosition);
		// } else {
		// title = fpyTs.get(childPosition);
		// }
		// Toast.makeText(getApplicationContext(), title,
		// Toast.LENGTH_SHORT).show();
		// return false;
		// }
		// });
	}

	private void initData() {
		arrows = new int[] { R.drawable.arrow_bottom, R.drawable.arrow_bottom };
		tasks = new ArrayList();
		tasks.add("我的任务");
		tasks.add("分配任务");

		myTs = new ArrayList();
		myTs.add("已经完成(1)");
		myTs.add("未完成(2)");
		myTs.add("将要开始的(12)");

		fpyTs = new ArrayList();
		fpyTs.add("已经完成(4)");
		fpyTs.add("未完成(8)");
		fpyTs.add("将要开始的(6)");
		String url = GlobalUrl.IP + GlobalUrl.getTasksCountInfo + "?loginName="
				+ loginName;
		System.out.println("url:" + url);
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					data = data.substring(1, data.length() - 1);
					System.out.println("***********data:" + data);
					Pattern datePattern = Pattern.compile("\"\\w*,\\w*,\\d*\"",
							Pattern.CASE_INSENSITIVE);
					Matcher matcher = datePattern.matcher(data);
					while (matcher.find()) {
						String a = matcher.group();
						System.out.println(a);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					error.printStackTrace();
				}
			};
			Map param_map = new HashMap();
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @todo 适配器
	 * @author pang
	 *
	 */
	private class MyExpandableListAdapter extends BaseExpandableListAdapter {

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

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

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

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(HomeActivity.this).inflate(
					R.layout.type_tasks, null);
			TextView task_title = (TextView) view.findViewById(R.id.task_title);
			task_title.setText(getChild(groupPosition, childPosition)
					.toString());
			return view;
		}

		@Override
		public int getGroupCount() {
			return tasks.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return tasks.get(groupPosition);
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(HomeActivity.this).inflate(
					R.layout.types, null);
			TextView group_title = (TextView) view
					.findViewById(R.id.group_title);
			group_title.setText(getGroup(groupPosition).toString());
			group_title.setTextSize(20);
			ImageView group_img = (ImageView) view.findViewById(R.id.group_img);
			if (isExpanded) {
				group_img.setImageResource(R.drawable.arrow_top);//
			} else {// �۵�
				group_img.setImageResource(R.drawable.arrow_bottom);//
			}

			return view;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	/**
	 * 
	 * @param v
	 * @user:pang
	 * @data:2015年7月21日
	 * @todo:创建任务
	 * @return:void
	 */
	public void create_task_ops(View v) {
		if (v.getId() == R.id.assigne_task) {// fenpei
			Intent intent = new Intent(HomeActivity.this,
					CreateTaskAssgineActivity.class);
			intent.putExtra("type", TaskEntity.TASK_TYPE_ASSIGN);
			startActivity(intent);
		} else if (v.getId() == R.id.approve_task) {// shenqing
			Intent intent = new Intent(HomeActivity.this,
					CreateTaskAssgineActivity.class);
			intent.putExtra("type", TaskEntity.TASK_TYPE_APPLY);
			startActivity(intent);
		} else if (v.getId() == R.id.crate_task) {// chuangjian
			Intent intent = new Intent(HomeActivity.this,
					CreateTaskAssgineActivity.class);
			intent.putExtra("type", TaskEntity.TASK_TYPE_CREATE);
			startActivity(intent);
		}
	}

}
