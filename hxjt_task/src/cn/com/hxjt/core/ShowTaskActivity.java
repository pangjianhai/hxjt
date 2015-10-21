package cn.com.hxjt.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.FileEntity;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.part.AttachmentAdapter;
import cn.com.hxjt.core.util.FileUtil;
import cn.com.hxjt.core.util.IApplyOps;
import cn.com.hxjt.core.util.IAttachmentOps;
import cn.com.hxjt.core.util.TaskUtil;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 任务详情activity
 * @author pang
 *
 */
public class ShowTaskActivity extends ParentTaskActivity implements IApplyOps,
		IAttachmentOps {
	// 任务ID
	private String taskId;
	private TaskBean tb = null;

	private TextView taskName, belongPro, belongProPosition, arranger,
			receiver, creator, requiredCompletionDate, completionDate,
			importantLevel, emergentLevel, show_runningState;

	private Button ops_sig, ops_cancel, ops_submit, ops_get, ops_approve,
			ops_edit;

	private ListView doc_lv;
	private List<FileEntity> ds = new ArrayList<FileEntity>();
	private AttachmentAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_task_detail);
		initPart();
		taskId = getIntent().getStringExtra("taskId");
		getDetail();
		renderBtn();
		renderDoc();
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
		ops_edit = (Button) findViewById(R.id.ops_edit);

		doc_lv = (ListView) findViewById(R.id.doc_lv);
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
				+ "?loginName=" + loginName + "&taskID=" + taskId;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					if (data == null || "".equals(data) || "[]".equals(data)) {
						return;
					}
					String newData = data.substring(1, data.length() - 1);
					String[] types = newData.split(",");
					Map<String, String> map = new HashMap();
					for (int i = 0; i < types.length; i++) {
						String ops = types[i].substring(1,
								types[i].length() - 1);
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
					if (map.containsKey("修改任务")) {
						ops_edit.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "哦，服务器出问题了",
							Toast.LENGTH_SHORT).show();
					error.printStackTrace();
				}
			};
			Map param_map = new HashMap();
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ops_cancel.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// TaskUtil.no_login_alter(v, ShowTaskActivity.this,
		// ShowTaskActivity.this);
		// }
		// });
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
			TaskUtil.no_login_alter(v, ShowTaskActivity.this,
					ShowTaskActivity.this);
			return;
		} else if (R.id.ops_edit == v.getId()) {
			Intent intent = new Intent(ShowTaskActivity.this,
					EditTaskActivity.class);
			intent.putExtra("taskId", taskId);
			startActivity(intent);
			finish();
			return;
		}
		StringBuilder param = new StringBuilder("");
		param.append("?loginName=" + loginName + "&taskID=").append(taskId);
		real_ops_for_task(url + param.toString(), v.getId());
	}

	/**
	 * 审核通过或者不通过
	 */
	@Override
	public void apply(boolean isPass) {
		String url = GlobalUrl.IP + GlobalUrl.approveTask;
		StringBuilder param = new StringBuilder("");
		param.append("?loginName=" + loginName + "&taskID=").append(taskId);
		param.append("&isApprovePass=").append(isPass);
		real_ops_for_task(url + param.toString(), R.id.ops_approve);
	}

	/**
	 * @param url
	 * @param param
	 * @param ops_type
	 * @user:pang
	 * @data:2015年10月23日
	 * @todo:真正进行业务处理的地方
	 * @return:void
	 */
	private void real_ops_for_task(String url, int ops_type) {
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					freshPage();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "哦，服务器出问题了",
							Toast.LENGTH_SHORT).show();
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
	 * @data:2015年10月23日
	 * @todo:刷新页面
	 * @return:void
	 */
	private void freshPage() {
		Intent intent = new Intent(ShowTaskActivity.this, HomeActivity.class);
		intent.putExtra("taskId", taskId);
		startActivity(intent);
		finish();
	}

	public void renderDoc() {
		adapter = new AttachmentAdapter(ShowTaskActivity.this,
				ShowTaskActivity.this, ds);
		doc_lv.setAdapter(adapter);
		// loadDocData();
	}

	private void loadDocData() {
		String url = GlobalUrl.IP + GlobalUrl.getTaskAttachmentInfos
				+ "?taskID=1333";
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					if (data == null || "".equals(data) || "[]".equals(data)) {
						return;
					}
					try {
						JSONArray array = new JSONArray(data);
						for (int i = 0; i < array.length(); i++) {
							JSONObject j = array.getJSONObject(i);
							String identifier = j.getString("Identifier");
							String name = j.getString("Name");
							FileEntity fe = new FileEntity();
							fe.setId(identifier);
							fe.setName(name);
							ds.add(fe);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					adapter.notifyDataSetChanged();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "哦，服务器出问题了",
							Toast.LENGTH_SHORT).show();
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
	public void delAtt(String path) {

	}

	@Override
	public void download(String attId, String attName) {
		// real_download(attId, attName);
	}

	/**
	 * 关于真正的下载
	 */
	private ProgressDialog dialog = null;

	private void real_download(String attId, String attName) {
		dialog = new ProgressDialog(this);
		dialog.setTitle("下载");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setCancelable(true);
		dialog.show();
		String url = GlobalUrl.IP + GlobalUrl.getAttachment
				+ "?attachmentIdentifier=" + attId;
		HttpUtils http = new HttpUtils();
		HttpHandler handler = http.download(url, FileUtil.getDownloadFileDir()
				+ "/" + attName, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() {

					@Override
					public void onStart() {
						dialog.setMessage("开始下载");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						int x = (int) (current * 100 / total);
						dialog.setProgress(x);
					}

					/**
					 * 下载成功之后给后台发送消息通知，同时移动端提醒下载完毕，最后对话框消失
					 */
					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						sendMsgForDownloadSuccess();
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "下载成功",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "下载失败",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	/**
	 * @user:pang
	 * @data:2015年8月9日
	 * @todo:下载成功后手机消息收到通知
	 * @return:void
	 */
	public void sendMsgForDownloadSuccess() {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.hxjt)// 设置图标
				.setContentTitle("通知")// 设置标题
				.setContentText("附件下载成功");// 设置内容

		Notification n = mBuilder.build();
		n.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动关闭通知
		// 设定默认震动
		n.defaults |= Notification.DEFAULT_VIBRATE;
		// 设定默认LED灯提醒
		n.defaults |= Notification.DEFAULT_LIGHTS;
		// 设置点击后通知自动清除
		n.defaults |= Notification.FLAG_AUTO_CANCEL;

		nm.notify(0, n);
	}

}
