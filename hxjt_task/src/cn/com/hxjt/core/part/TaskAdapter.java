package cn.com.hxjt.core.part;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.hxjt.core.R;
import cn.com.hxjt.core.entity.TaskBean;

/**
 * @TODO 任务适配器
 * @author pang
 *
 */
public class TaskAdapter extends BaseAdapter {
	private List<TaskBean> dataSourceList = new ArrayList<TaskBean>();
	private HolderView holder;
	private Context context;

	public TaskAdapter(Context context, List<TaskBean> dataSourceList) {
		super();
		this.context = context;
		this.dataSourceList = dataSourceList;
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
		TaskBean t = dataSourceList.get(position);
		System.out.println("I:" + position + "-->" + t);
		if (convertview == null) {
			convertview = View.inflate(context, R.layout.tasks_list_item, null);
			holder.item_task_name = (TextView) convertview
					.findViewById(R.id.item_task_name);
			convertview.setTag(holder);
		} else {
			holder = (HolderView) convertview.getTag();
		}
		holder.item_task_name.setText(t.getName());
		return convertview;
	}

	private class HolderView {
		private TextView item_task_name;
	}

}
