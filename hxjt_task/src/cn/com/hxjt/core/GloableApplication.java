package cn.com.hxjt.core;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class GloableApplication extends Application {
	private static final String TAG = "JPush";

	private static String loginName = "";

	private static String chineseName = "";

	public static void setLoginName(String str) {
		loginName = str;
	}

	public static String getLoginName() {
		return loginName;
	}

	public static String getChineseName() {
		return chineseName;
	}

	public static void setChineseName(String chineseName) {
		GloableApplication.chineseName = chineseName;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "[ExampleApplication] onCreate");
		super.onCreate();

		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
	}

}
