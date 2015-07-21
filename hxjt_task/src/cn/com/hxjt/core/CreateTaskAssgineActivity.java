package cn.com.hxjt.core;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @todo 分配任务activity
 * @author pang
 *
 */
public class CreateTaskAssgineActivity extends ParentTaskActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_task_assigned);
	}

	/**
	 * 
	 * @param v
	 * @user:pang
	 * @data:2015年7月21日
	 * @todo:创建任务
	 * @return:void
	 */
	public void create_task_ops(View v) {
	}
}
