package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.Window;
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
			receiver, creator, requiredCompletionDate, completionDate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_task_detail);
		System.out.println("initPart");
		initPart();
		taskId = getIntent().getStringExtra("taskId");
		getDetail();
	}

	/**
	 * @user:pang
	 * @data:2015年10月10日
	 * @todo:初始化元素
	 * @return:void
	 */
	private void initPart() {
		taskName = (TextView) findViewById(R.id.taskName);
		belongPro = (TextView) findViewById(R.id.belongPro);
		belongProPosition = (TextView) findViewById(R.id.belongProPosition);
		arranger = (TextView) findViewById(R.id.arranger);
		receiver = (TextView) findViewById(R.id.receiver);
		creator = (TextView) findViewById(R.id.creator);
		requiredCompletionDate = (TextView) findViewById(R.id.requiredCompletionDate);
		completionDate = (TextView) findViewById(R.id.completionDate);
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
		arranger.setText(tb.getArranger());
		receiver.setText(tb.getReceiver());
		creator.setText(tb.getCreator());
		requiredCompletionDate.setText(tb.getRequireCompleteDate());
		String ct = tb.getCompleteDate();
		if ("null".equals(ct)) {
			ct = "";
		}
		completionDate.setText(ct);
	}

}
