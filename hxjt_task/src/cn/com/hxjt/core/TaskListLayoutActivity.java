package cn.com.hxjt.core;

import android.os.Bundle;
import android.view.Window;

public class TaskListLayoutActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tasks_list_layout);

	}
}
