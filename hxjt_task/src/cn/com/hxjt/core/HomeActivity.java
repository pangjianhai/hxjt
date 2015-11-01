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
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskEntity;
import cn.com.hxjt.core.entity.TaskNumBean;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 首页
 * @author pang
 *
 */
public class HomeActivity extends BaseActivity {

	private ProgressBar main_progressBar1;
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

	List<TaskNumBean> myNumList = new ArrayList();
	List<TaskNumBean> assignNumList = new ArrayList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initPart();
		initAlis();
	}

	private void initPart() {
		main_progressBar1 = (ProgressBar) findViewById(R.id.main_progressBar1);
		initData();
		mExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
		mExpandableListView.setAdapter(new MyExpandableListAdapter());
		mExpandableListView.setGroupIndicator(null);
		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				int type = 0;
				String param = "";
				String url = GlobalUrl.IP;
				TaskNumBean tnb = null;
				if (groupPosition == 0) {
					url = url + GlobalUrl.getMyTask;
					tnb = myNumList.get(childPosition);
				} else {
					url = url + GlobalUrl.getArrangedTask;
					tnb = assignNumList.get(childPosition);
				}
				url = url + "?loginName=" + loginName + "&taskType="
						+ tnb.getType();
				type = groupPosition;
				param = tnb.getType();
				Intent intent = new Intent(HomeActivity.this,
						TaskListLayoutActivity.class);
				intent.putExtra("type", type);
				intent.putExtra("param", param);
				startActivity(intent);
				return false;
			}
		});
	}

	private void initData() {
		main_progressBar1.setVisibility(View.VISIBLE);
		arrows = new int[] { R.drawable.arrow_bottom, R.drawable.arrow_bottom };
		tasks = new ArrayList();
		tasks.add("我的任务");
		tasks.add("分配任务");

		String url = GlobalUrl.IP + GlobalUrl.getTasksCountInfo + "?loginName="
				+ loginName;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					data = data.substring(1, data.length() - 1);
					Pattern datePattern = Pattern.compile("\"\\w*,\\w*,\\d*\"",
							Pattern.CASE_INSENSITIVE);
					Matcher matcher = datePattern.matcher(data);
					while (matcher.find()) {
						String orginalStr = matcher.group();
						orginalStr = orginalStr.substring(1,
								orginalStr.length() - 1);
						String[] str = orginalStr.split(",");
						String type = str[0];
						String param = str[1];
						String num = str[2];
						String display = "";
						TaskNumBean tnb = new TaskNumBean();
						if ("Today".equals(param)) {
							param = "Today";
							display = "今天";
						} else if ("Tomorrow".equals(param)) {
							param = "Tomorrow";
							display = "明天";
						} else if ("ThiwWeek".equals(param)) {
							param = "ThisWeek";
							display = "本周";
						} else if ("NextWeek".equals(param)) {
							param = "NextWeek";
							display = "下周";
						} else if ("Later".equals(param)) {
							param = "Later";
							display = "即将";
						} else if ("LongLater".equals(param)) {
							param = "LongLater";
							display = "以后再说";
						} else if ("Overdue".equals(param)) {
							param = "Overdue";
							display = "逾期";
						} else if ("All".equals(param)) {
							param = "All";
							display = "所有";
						}
						tnb.setDisplay(display);
						tnb.setType(param);
						tnb.setNum(num);

						if ("MyTask".equals(type)) {
							myNumList.add(tnb);
						} else if ("ArrangeTask".equals(type)) {
							assignNumList.add(tnb);
						}

					}
					render();
					main_progressBar1.setVisibility(View.GONE);
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					main_progressBar1.setVisibility(View.GONE);
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
	 * @user:pang
	 * @data:2015年10月13日
	 * @todo:渲染一棵树
	 * @return:void
	 */
	private void render() {
		mExpandableListView.expandGroup(0);
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
				total = myNumList.size();
			} else {
				total = assignNumList.size();
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
			TaskNumBean t = null;
			if (groupPosition == 0) {
				t = myNumList.get(childPosition);
				title = t.getDisplay() + "(" + t.getNum() + ")";
			} else {
				t = assignNumList.get(childPosition);
				title = t.getDisplay() + "(" + t.getNum() + ")";
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

	@Override
	public void onRestart() {
		super.onRestart();
	}

}
