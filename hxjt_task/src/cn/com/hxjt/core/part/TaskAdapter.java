package cn.com.hxjt.core.part;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.hxjt.core.GloableApplication;
import cn.com.hxjt.core.R;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.util.TaskClickOps;

/**
 * @TODO 任务适配器
 * @author pang
 *
 */
public class TaskAdapter extends BaseAdapter {
	private List<TaskBean> dataSourceList = new ArrayList<TaskBean>();
	private HolderView holder;
	private Context context;
	private TaskClickOps ops;
	private String taskType;

	public TaskAdapter(Context context, TaskClickOps ops,
			List<TaskBean> dataSourceList, String taskType) {
		super();
		this.context = context;
		this.ops = ops;
		this.dataSourceList = dataSourceList;
		this.taskType = taskType;
	}

	@Override
	public int getCount() {
		return dataSourceList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataSourceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup parent) {
		holder = new HolderView();
		final TaskBean t = dataSourceList.get(position);
		if (convertview == null) {
			convertview = View.inflate(context, R.layout.tasks_list_item, null);
			holder.item_task_name = (TextView) convertview
					.findViewById(R.id.item_task_name);
			holder.item_task_time = (TextView) convertview
					.findViewById(R.id.item_task_time);
			holder.item_task_mine = (TextView) convertview
					.findViewById(R.id.item_task_mine);
			holder.task_item_single = (RelativeLayout) convertview
					.findViewById(R.id.task_item_single);
			convertview.setTag(holder);
		} else {
			holder = (HolderView) convertview.getTag();
		}
		String creator = t.getCreator();
		String arranger = t.getArranger();
		String receiver = t.getReceiver();
		String cu = GloableApplication.getChineseName();
		StringBuffer mine = new StringBuffer("");
		if ("0".equals(taskType)) {
			mine.append("[" + arranger + "]安排的任务");
		} else if ("1".equals(taskType)) {
			mine.append("安排给[" + receiver + "]的任务");
		}
		holder.item_task_mine.setText(mine);
		holder.item_task_name.setText(t.getName());

		holder.task_item_single.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ops.clickTask(t.getId());
			}
		});
		holder.item_task_time.setText(t.getRequireCompleteDate());
		return convertview;
	}

	private class HolderView {
		private TextView item_task_name, item_task_time, item_task_mine;
		private RelativeLayout task_item_single;
	}

}
