package cn.com.hxjt.core;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.util.CommonDateUtil;
import cn.com.hxjt.core.util.TaskUtil;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 编辑任务
 * @author pang
 *
 */
public class EditTaskActivity extends ParentTaskActivity implements
		View.OnTouchListener {
	public static final int FILE_RESULT_CODE = 1;
	private String taskId;
	private TaskBean tb = null;
	private EditText taskName;
	private Spinner projectType, belongPro, belongProPosition;
	private ArrayAdapter<String> proTypesAd = null;
	private ArrayAdapter<String> proPositionsAd = null;
	private ArrayAdapter<String> proAd = null;
	private EditText st;

	// old
	private String old_pro_t;
	private String old_pro;
	private String old_pro_p;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_task_assigned);
		taskId = getIntent().getStringExtra("taskId");
		initPart();
		getDetail();
	}

	/**
	 * @user:pang
	 * @data:2015年10月22日
	 * @todo:初始化控件
	 * @return:void
	 */
	private void initPart() {
		taskName = (EditText) findViewById(R.id.taskName);
		projectType = (Spinner) findViewById(R.id.projectType);
		belongPro = (Spinner) findViewById(R.id.belongPro);
		belongProPosition = (Spinner) findViewById(R.id.belongProPosition);
		st = (EditText) findViewById(R.id.st);
		st.setOnTouchListener(this);
		String date = CommonDateUtil.getCurrTime();
		st.setText(date.subSequence(0, date.length() - 3));

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
					render(tb);
					initProType();
					projectType
							.setOnItemSelectedListener(new OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									String proType = proTypesAd.getItem(arg2);
									getPro(proType);
									getProPosition(proType);
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {

								}
							});
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

	private void initProType() {
		String url = GlobalUrl.IP + GlobalUrl.getProTypes;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					// ["数据仓库建设","软件开发","未知"]
					String newData = data.substring(1, data.length() - 1);
					String[] types = newData.split(",");
					String[] nt = new String[types.length];
					for (int i = 0; i < nt.length; i++) {
						nt[i] = types[i].substring(1, types[i].length() - 1);
					}
					proTypesAd = new ArrayAdapter<String>(
							EditTaskActivity.this,
							android.R.layout.simple_spinner_item, nt);
					projectType.setAdapter(proTypesAd);
					int index = proTypesAd.getPosition(old_pro_t);
					projectType.setSelection(index, true);
				}

				@Override
				public void onFailure(HttpException error, String msg) {
				}
			};
			Map param_map = new HashMap();
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPro(String proT) {
		String url = GlobalUrl.IP + GlobalUrl.getProByType + "?projectType="
				+ proT;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					String[] nt = {};
					if (data == null || "".equals(data) || "[]".equals(data)) {
					} else {
						// ["数据仓库建设","软件开发","未知"]
						String newData = data.substring(1, data.length() - 1);
						String[] types = newData.split(",");
						nt = new String[types.length];
						for (int i = 0; i < nt.length; i++) {
							nt[i] = types[i]
									.substring(1, types[i].length() - 1);
						}
					}
					proAd = new ArrayAdapter<String>(EditTaskActivity.this,
							android.R.layout.simple_spinner_item, nt);
					belongPro.setAdapter(proAd);
					// 制定位置
					if (old_pro != null && !"".equals(old_pro)
							&& !"null".equals(old_pro)) {
						if (nt != null && nt.length > 0) {
							int index = proAd.getPosition(old_pro);
							if (index >= 0) {
								belongPro.setSelection(index, true);
							}
						}
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
				}
			};
			Map param_map = new HashMap();
			param_map.put("projectType", proT);
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getProPosition(String proT) {
		String url = GlobalUrl.IP + GlobalUrl.getProjectPositions
				+ "?projectType=" + proT;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					String[] nt = {};
					if (data == null || "".equals(data) || "[]".equals(data)) {
					} else {
						String newData = data.substring(1, data.length() - 1);
						String[] types = newData.split(",");
						nt = new String[types.length];
						for (int i = 0; i < nt.length; i++) {
							nt[i] = types[i]
									.substring(1, types[i].length() - 1);
						}
					}
					proPositionsAd = new ArrayAdapter<String>(
							EditTaskActivity.this,
							android.R.layout.simple_spinner_item, nt);
					belongProPosition.setAdapter(proPositionsAd);

					if (old_pro_p != null && !"".equals(old_pro_p)
							&& !"null".equals(old_pro_p)) {
						if (nt != null && nt.length > 0) {
							int index = proPositionsAd.getPosition(old_pro_p);
							if (index >= 0) {
								belongProPosition.setSelection(index, true);
							}
						}
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
				}
			};
			Map param_map = new HashMap();
			send_normal_request(url, param_map, rcb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.date_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view
					.findViewById(R.id.date_picker);
			final TimePicker timePicker = (android.widget.TimePicker) view
					.findViewById(R.id.time_picker);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH), null);

			timePicker.setIs24HourView(true);
			timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(Calendar.MINUTE);

			if (v.getId() == R.id.st) {
				final int inType = st.getInputType();
				st.setInputType(InputType.TYPE_NULL);
				st.onTouchEvent(event);
				st.setInputType(inType);
				st.setSelection(st.getText().length());

				builder.setTitle("选取时间");
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								sb.append("  ");
								sb.append(timePicker.getCurrentHour())
										.append(":")
										.append(timePicker.getCurrentMinute());

								st.setText(sb);
								st.requestFocus();

								dialog.cancel();
							}
						});

			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	/**
	 * @param tb
	 * @user:pang
	 * @data:2015年10月22日
	 * @todo:渲染界面
	 * @return:void
	 */
	public void render(TaskBean tb) {
		taskName.setText(tb.getName());
		old_pro_t = tb.getProjectType();
		old_pro = tb.getPro();
		old_pro_p = tb.getProPosition();
		String time = tb.getRequireCompleteDate();
		time = time.replace("T", " ");
		time = time.substring(0, time.length() - 3);
		st.setText(time);
	}

	/**
	 * 
	 * @param v
	 * @user:pang
	 * @data:2015年7月21日
	 * @todo:保存任务
	 * @return:void
	 */
	public void save_or_cancel(View v) {
		if (v.getId() == R.id.add_save_task) {
			StringBuilder param = new StringBuilder("?");
			param.append("taskID=" + taskId);
			param.append("&loginName=" + loginName);
			param.append("&taskName=" + taskName.getText().toString());
			String type = projectType.getSelectedItem() != null ? projectType
					.getSelectedItem().toString() : "";
			param.append("&projectType=" + type);
			String ps = belongProPosition.getSelectedItem() != null ? belongProPosition
					.getSelectedItem().toString() : "";
			param.append("&projectPosition=" + ps);
			String pro = belongPro.getSelectedItem() != null ? belongPro
					.getSelectedItem().toString() : "";
			param.append("&project=" + pro);
			String st_str = st.getText().toString() + ":00";
			Date dt = CommonDateUtil.getTime(st_str);
			String str = CommonDateUtil.getDateTimeForStr(dt);
			param.append("&requiredCompletionDate=" + str);
			update(param.toString());
		} else if (v.getId() == R.id.add_cancel_task) {
			finish();
		}
	}

	/**
	 * 
	 * @param param
	 * @user:pang
	 * @data:2015年9月30日
	 * @todo:修改任务
	 * @return:void
	 */
	private void update(String param) {
		String url = GlobalUrl.IP + GlobalUrl.updateTask;
		url = url + param;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					toHome();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "服务器出问题了",
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
	 * 
	 * @param taskId
	 * @user:pang
	 * @data:2015年9月28日
	 * @todo:返回首页
	 * @return:void
	 */
	private void toHome() {
		Intent intent = new Intent(EditTaskActivity.this, HomeActivity.class);
		startActivity(intent);
	}
}
