package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskEntity;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

/**
 * @todo 任务详情activity
 * @author pang
 *
 */
public class ShowTaskActivity extends ParentTaskActivity {
	// 任务ID
	private String taskId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_task_detail);
		taskId = getIntent().getStringExtra("taskId");
		getDetail();
	}

	/**
	 * @user:pang
	 * @data:2015年9月28日
	 * @todo:获取任务详情
	 * @return:void
	 */
	private void getDetail() {
		String url = GlobalUrl.IP + GlobalUrl.getTaskInfo + "?taskID=" + taskId;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					try {
						JSONObject j = new JSONObject(data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "哦，服务器出问题了",
							Toast.LENGTH_SHORT).show();
				}
			};
			Map param_map = new HashMap();
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
