package cn.com.hxjt.core;

import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import cn.com.hxjt.core.util.ActivityCollector;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @TODO 父类
 * @author pang
 *
 */
public class BaseActivity extends Activity {
	public String userId;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		ActivityCollector.addActivity(this);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	public boolean isNetWorkConnected() {
		ConnectivityManager cm = (ConnectivityManager) BaseActivity.this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable()) {
			return true;
		}
		return false;
	}

	public void send_normal_request(String url, Map<String, String> p,
			RequestCallBack<?> rcb) {
		if (!isNetWorkConnected()) {
			Toast.makeText(getApplicationContext(), "无法连接网络",
					Toast.LENGTH_SHORT).show();
			return;
		}
		RequestParams params = new RequestParams();
		if (p != null) {
			Iterator<Map.Entry<String, String>> it = p.entrySet().iterator();
			/**
			 * ��Ӳ���
			 */
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				params.addBodyParameter(entry.getKey(), entry.getValue());
			}
		}
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params, rcb);
	}

}
