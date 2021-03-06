package cn.com.hxjt.core.part;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.hxjt.core.R;
import cn.com.hxjt.core.entity.FileEntity;
import cn.com.hxjt.core.entity.TaskBean;
import cn.com.hxjt.core.util.IAttachmentOps;

/**
 * @TODO 任务适配器
 * @author pang
 *
 */
public class AttachmentAdapter extends BaseAdapter {
	private List<FileEntity> dataSourceList = new ArrayList<FileEntity>();
	private HolderView holder;
	private Context context;
	private IAttachmentOps ops;

	public AttachmentAdapter(Context context, IAttachmentOps ops,
			List<FileEntity> dataSourceList) {
		super();
		this.context = context;
		this.ops = ops;
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
		final FileEntity t = dataSourceList.get(position);
		final String attId = t.getId();
		final String attName = t.getName();
		if (convertview == null) {
			convertview = View.inflate(context, R.layout.showdoc_list_item,
					null);
			holder.show_att_name = (TextView) convertview
					.findViewById(R.id.show_att_name);
			convertview.setTag(holder);
		} else {
			holder = (HolderView) convertview.getTag();
		}
		holder.show_att_name.setText(t.getName());

		holder.show_att_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ops.download(attId, attName);
			}
		});
		return convertview;
	}

	private class HolderView {
		private TextView show_att_name;
	}

}
