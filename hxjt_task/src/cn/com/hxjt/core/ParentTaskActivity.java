package cn.com.hxjt.core;

import android.os.Bundle;
import cn.com.hxjt.core.util.ActivityCollector;

/**
 * @todo 任务父类
 * @author pang
 *
 */
public class ParentTaskActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}
}
