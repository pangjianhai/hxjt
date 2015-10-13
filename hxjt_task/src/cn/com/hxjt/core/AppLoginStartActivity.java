package cn.com.hxjt.core;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import cn.com.hxjt.core.cons.GlobalUrl;
import cn.com.hxjt.core.part.LineEditText;
import cn.com.hxjt.core.part.SharedPreInto;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @author pang
 * @todo 登陆框
 *
 */
public class AppLoginStartActivity extends ParentTaskActivity {

	private LineEditText mUser; // 帐号编辑框
	private LineEditText mPassword; // 密码编辑框

	/**
	 * 保存到手机本地的账号信息
	 */
	private String loginName, name, pwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		validBeforeRender();
		setContentView(R.layout.app_login);
		mUser = (LineEditText) findViewById(R.id.login_user_edit);
		mPassword = (LineEditText) findViewById(R.id.login_passwd_edit);

	}

	private void validBeforeRender() {
		String local_loginName = new SharedPreInto(AppLoginStartActivity.this)
				.getSharedFieldValue("loginName");
		String local_pwd = new SharedPreInto(AppLoginStartActivity.this)
				.getSharedFieldValue("password");
		if (local_loginName != null && !"".equals(local_loginName)
				&& local_pwd != null && !"".equals(local_pwd)) {
			loginName = local_loginName;
			pwd = local_pwd;
			realLogin(local_loginName, local_pwd);
		}

	}

	/**
	 * 
	 * @tags @param v
	 * @date 2015年5月11日
	 * @todo 开始验证登陆
	 * @author pang
	 */
	public void loginHealthApp(View v) {
		// loginOk();
		try {
			loginName = mUser.getText().toString();
			pwd = mPassword.getText().toString();
			/**
			 * 如果用户名密码为空则不能登录
			 */
			if (loginName == null || "".equals(loginName) || pwd == ""
					|| "".equals(pwd)) {
				loginFail();
				return;
			}
			realLogin(loginName, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param j
	 * @user:pang
	 * @data:2015年6月29日
	 * @todo:真正的请求验证用户名密码
	 * @return:void
	 */
	private void realLogin(String loginName, String pwd) {
		String url = GlobalUrl.IP + GlobalUrl.login;
		url = url + "?loginName=" + loginName + "&pwd=" + pwd;
		try {
			RequestCallBack<String> rcb = new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String data = responseInfo.result;
					if (data == null || "".equals(data) || "null".equals(data)) {
						loginFail();
					}
					try {
						JSONObject j = new JSONObject(data);
						// 返回的JSON有Name字段说明登录成功否则为失败
						if (j.has("Name")) {
							name = j.getString("Name");
							loginOk();
						} else {
							loginFail();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(getApplicationContext(), "网络有问题哦",
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
	 * @tags
	 * @date 2015年5月11日
	 * @todo 登录成功
	 * @author pang
	 */
	private void loginOk() {
		/**
		 * 登陆成功保存本地
		 */
		new SharedPreInto(AppLoginStartActivity.this).initAccountAfterReg(
				loginName, name, pwd);
		/**
		 * 全局变量设置
		 */
		GloableApplication.setLoginName(loginName);
		Intent intent = new Intent();
		intent.setClass(AppLoginStartActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * 
	 * @tags
	 * @date 2015年5月11日
	 * @todo 登录失败
	 * @author pang
	 */
	private void loginFail() {
		Toast.makeText(getApplicationContext(), "鉴权失败，账号有误", Toast.LENGTH_SHORT)
				.show();
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

}
