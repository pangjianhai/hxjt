package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.util.TaskUtil;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 任务详情activity
 * @author pang
 *
 */
public class ShowTaskActivity extends ParentTaskActivity {
	// 任务ID
	private String taskId;
	private TaskBean tb = null;

	private TextView taskName, belongPro, belongProPosition, arranger,
			receiver, creator, requiredCompletionDate, completionDate,
			importantLevel, emergentLevel, show_runningState;

	private Button ops_sig, ops_cancel, ops_submit, ops_get, ops_approve;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_task_detail);
		System.out.println("initPart");
		initPart();
		taskId = "1180";// getIntent().getStringExtra("taskId");
		getDetail();
		renderBtn();
	}

	/**
	 * @user:pang
	 * @data:2015年10月10日
	 * @todo:初始化元素
	 * @return:void
	 */
	private void initPart() {
		taskName = (TextView) findViewById(R.id.show_taskName);
		belongPro = (TextView) findViewById(R.id.show_belongPro);
		belongProPosition = (TextView) findViewById(R.id.show_belongProPosition);
		arranger = (TextView) findViewById(R.id.show_arranger);
		receiver = (TextView) findViewById(R.id.show_receiver);
		creator = (TextView) findViewById(R.id.show_creator);
		requiredCompletionDate = (TextView) findViewById(R.id.show_requiredCompletionDate);
		completionDate = (TextView) findViewById(R.id.show_completionDate);
		importantLevel = (TextView) findViewById(R.id.show_important);
		emergentLevel = (TextView) findViewById(R.id.show_emergent);
		show_runningState = (TextView) findViewById(R.id.show_runningState);

		ops_sig = (Button) findViewById(R.id.ops_sig);
		ops_cancel = (Button) findViewById(R.id.ops_cancel);
		ops_submit = (Button) findViewById(R.id.ops_submit);
		ops_get = (Button) findViewById(R.id.ops_get);
		ops_approve = (Button) findViewById(R.id.ops_approve);
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
					tb = TaskUtil.getBeanByJson(data);
					System.out.println(tb + ":data:" + data);
					renderFace(tb);
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

	/**
	 * 
	 * @param tb
	 * @user:pang
	 * @data:2015年10月10日
	 * @todo:渲染界面
	 * @return:void
	 */
	private void renderFace(TaskBean tb) {
		taskName.setText(tb.getName());
		belongPro.setText(tb.getPro());
		belongProPosition.setText(tb.getProPosition());
		show_runningState.setText(tb.getRunningState());
		arranger.setText(tb.getArranger());
		receiver.setText(tb.getReceiver());
		creator.setText(tb.getCreator());
		requiredCompletionDate.setText(tb.getRequireCompleteDate());
		String ct = tb.getCompleteDate();
		if ("null".equals(ct)) {
			ct = "";
		}
		completionDate.setText(ct);
		importantLevel.setText(tb.getImportantLevel());
		emergentLevel.setText(tb.getEmergentLevel());
	}

	/**
	 * @user:pang
	 * @data:2015年10月23日
	 * @todo:渲染可进行的操作按钮
	 * @return:void
	 */
	private void renderBtn() {
		String url = GlobalUrl.IP + GlobalUrl.getAvailableOpsByTaskAndUser
				+ "?loginName=" + loginName + "taskID=" + taskId;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					System.out.println("***********data:" + data);
					if (data == null || "".equals(data) || "[]".equals(data)) {
						return;
					}
					String newData = data.substring(1, data.length() - 1);
					String[] types = newData.split(",");
					Map<String, String> map = new HashMap();
					for (int i = 0; i < types.length; i++) {
						String ops = types[i].substring(1,
								types[i].length() - 1);
						System.out.println("ops:" + ops);
						map.put(ops, ops);
					}
					if (map.containsKey("签收任务")) {
						ops_sig.setVisibility(View.VISIBLE);
					}
					if (map.containsKey("提交任务")) {
						ops_submit.setVisibility(View.VISIBLE);
					}
					if (map.containsKey("取消任务")) {
						ops_cancel.setVisibility(View.VISIBLE);
					}
					if (map.containsKey("审核任务")) {
						ops_approve.setVisibility(View.VISIBLE);
					}
					if (map.containsKey("接受任务申请")) {
						ops_get.setVisibility(View.VISIBLE);
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

	/**
	 * @param v
	 * @user:pang
	 * @data:2015年10月23日
	 * @todo:操作
	 * @return:void
	 */
	public void ops_task(View v) {
		String url = null;
		if (R.id.ops_sig == v.getId()) {
			url = GlobalUrl.IP + GlobalUrl.signForTask;
		} else if (R.id.ops_cancel == v.getId()) {
			url = GlobalUrl.IP + GlobalUrl.cancelTask;
		} else if (R.id.ops_submit == v.getId()) {
			url = GlobalUrl.IP + GlobalUrl.submitTask;
		} else if (R.id.ops_get == v.getId()) {
			url = GlobalUrl.IP + GlobalUrl.acceptTaskApply;
		} else if (R.id.ops_approve == v.getId()) {
			url = GlobalUrl.IP + GlobalUrl.approveTask;
		}
	}

	/**
	 * @user:pang
	 * @data:2015年10月23日
	 * @todo:刷新页面
	 * @return:void
	 */
	private void freshPage() {
		Intent intent = new Intent(ShowTaskActivity.this,
				ShowTaskActivity.class);
		intent.putExtra("taskId", taskId);
		startActivity(intent);
		finish();
	}

}
