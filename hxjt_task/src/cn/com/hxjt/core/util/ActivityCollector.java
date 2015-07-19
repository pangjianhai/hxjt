package cn.com.hxjt.core.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * @todo activity管理器
 * @author pang
 *
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

	/**
	 * 
	 * @param activity
	 * @user:pang
	 * @data:2015年7月19日
	 * @todo:添加activity
	 * @return:void
	 */
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	/**
	 * 
	 * @param activity
	 * @user:pang
	 * @data:2015年7月19日
	 * @todo:移除activity
	 * @return:void
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	/**
	 * @user:pang
	 * @data:2015年7月19日
	 * @todo:移除所有的activity
	 * @return:void
	 */
	public static void finishAll() {
		for (Activity activity : activities) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
