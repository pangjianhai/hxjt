package cn.com.hxjt.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.part.TaskAdapter;
import cn.com.hxjt.core.util.TaskClickOps;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 不同类型任务一览
 * @author pang
 *
 */
public class TaskListLayoutActivity extends BaseActivity implements
		TaskClickOps {
	private ListView tasks_lv;
	private List<TaskBean> ds = new ArrayList<TaskBean>();
	private TaskAdapter adapter = null;
	/**
	 * 参数
	 */
	int type = 0;
	String param = "";
	String url = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tasks_list_layout);
		buildUrl();
		initPart();
		loadData();
	}

	private void buildUrl() {
		Intent it = getIntent();
		type = it.getIntExtra("type", 0);
		param = it.getStringExtra("param");
		url = GlobalUrl.IP;
		System.out.println("detail:" + type);
		if (type == 0) {
			url = url + GlobalUrl.getMyTask;
		} else {
			url = url + GlobalUrl.getArrangedTask;
		}
		url = url + "?loginName=" + loginName + "&taskType=" + param;
		System.out.println("url:" + url);
	}

	/**
	 * @user:pang
	 * @data:2015年10月13日
	 * @todo:初始化组件
	 * @return:void
	 */
	private void initPart() {
		tasks_lv = (ListView) findViewById(R.id.tasks_lv);
		adapter = new TaskAdapter(TaskListLayoutActivity.this,
				TaskListLayoutActivity.this, ds);
		tasks_lv.setAdapter(adapter);
	}

	/**
	 * @user:pang
	 * @data:2015年10月13日
	 * @todo:加载数据
	 * @return:void
	 */
	private void loadData() {
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					System.out.println("responseInfo:" + data);
					if ("[]".equals(data)) {
						return;
					}
					try {
						JSONArray array = new JSONArray(data);
						if (array.length() > 0) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject j = array.getJSONObject(i);
								String id = j.getString("ID");
								String name = j.getString("Name");
								TaskBean tb = new TaskBean();
								tb.setId(id);
								tb.setName(name);
								ds.add(tb);
							}
							adapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
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

	@Override
	public void clickTask(String taskId) {
		Intent intent = new Intent(TaskListLayoutActivity.this,
				ShowTaskActivity.class);
		intent.putExtra("taskId", taskId);
		startActivity(intent);
	}
}
