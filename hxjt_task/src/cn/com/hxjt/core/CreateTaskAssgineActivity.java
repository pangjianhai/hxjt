package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import cn.com.hxjt.core.cons.GlobalUrl;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @todo 分配任务activity
 * @author pang
 *
 */
public class CreateTaskAssgineActivity extends ParentTaskActivity {

	private EditText taskName;
	private Spinner projectType, belongPro, receiver;
	private ArrayAdapter<String> proTypesAd = null;
	private ArrayAdapter<String> userAd = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_task_assigned);
		initPart();
		initProType();
		initUser();
	}

	private void initPart() {
		taskName = (EditText) findViewById(R.id.taskName);
		projectType = (Spinner) findViewById(R.id.projectType);
		belongPro = (Spinner) findViewById(R.id.belongPro);
		receiver = (Spinner) findViewById(R.id.receiver);
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
							CreateTaskAssgineActivity.this,
							android.R.layout.simple_spinner_item, nt);
					projectType.setAdapter(proTypesAd);
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

	private void initUser() {
		String url = GlobalUrl.IP + GlobalUrl.getUsers;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					System.out.println("data:" + data);
					String[] nt = null;
					try {
						JSONArray array = new JSONArray(data);
						nt = new String[array.length()];
						for (int i = 0; i < array.length(); i++) {
							JSONObject o = (JSONObject) array.get(i);
							System.out.println("o:" + o);
							String loginName = o.getString("LoginName");
							nt[i] = loginName;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					userAd = new ArrayAdapter<String>(
							CreateTaskAssgineActivity.this,
							android.R.layout.simple_spinner_item, nt);
					receiver.setAdapter(userAd);
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

	/**
	 * 
	 * @param v
	 * @user:pang
	 * @data:2015年7月21日
	 * @todo:创建任务
	 * @return:void
	 */
	public void save_or_cancel(View v) {
	}
}
