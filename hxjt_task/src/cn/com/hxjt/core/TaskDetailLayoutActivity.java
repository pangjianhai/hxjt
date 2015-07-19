package cn.com.hxjt.core;

import android.os.Bundle;
import android.view.Window;

/**
 * @todo 任务详情
 * @author pang
 *
 */
public class TaskDetailLayoutActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.task_detail_layout);

	}
}
