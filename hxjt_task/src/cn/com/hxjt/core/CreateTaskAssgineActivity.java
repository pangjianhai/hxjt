package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

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
	private Spinner belongPro;
	private ArrayAdapter<String> proTypesAd = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("***************************onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_task_assigned);
		initPart();
		initProType();
	}

	private void initPart() {
		taskName = (EditText) findViewById(R.id.taskName);
		belongPro = (Spinner) findViewById(R.id.belongPro);
	}

	private void initProType() {
		String url = GlobalUrl.IP + GlobalUrl.getProTypes;
		System.out.println("*********url:" + url);
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					// ["数据仓库建设","软件开发","未知"]
					System.out.println("data:" + data);
					String newData = data.substring(1, data.length() - 1);
					String[] types = newData.split(",");
					String[] nt = new String[types.length];
					for (int i = 0; i < nt.length; i++) {
						nt[i] = types[i].substring(1, types[i].length() - 1);
					}
					proTypesAd = new ArrayAdapter<String>(
							CreateTaskAssgineActivity.this,
							android.R.layout.simple_spinner_item, nt);
					belongPro.setAdapter(proTypesAd);
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
