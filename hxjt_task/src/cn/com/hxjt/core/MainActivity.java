package cn.com.hxjt.core;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ExpandableListView mExpandableListView;

	private int[] arrows;

	private List<String> tasks;

	private List<String> myTs;
	private List<String> fpyTs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initPart();
	}

	private void initData() {
		arrows = new int[] { R.drawable.arrow_bottom, R.drawable.arrow_bottom };
		tasks = new ArrayList();
		tasks.add("�ҵ�����");
		tasks.add("��������");

		myTs = new ArrayList();
		myTs.add("�Ѿ���ɵ�");
		myTs.add("δ��ɵ�(2)");
		myTs.add("��Ҫ��ʼ��(12)");

		fpyTs = new ArrayList();
		fpyTs.add("�Ѿ���ɵ�(8)");
		fpyTs.add("δ��ɵ�(2)");
		fpyTs.add("��Ҫ��ʼ��(12)");
	}

	/**
	 * 
	 * 
	 * @user:pang
	 * @data:2015��7��19��
	 * @todo:��ʼ��
	 * @return:void
	 */
	private void initPart() {

		// ׼��������
		initData();

		mExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
		// ȥ����ͷ
		mExpandableListView.setGroupIndicator(null);
		// ����adapter����adapterҪ����
		mExpandableListView.setAdapter(new MyExpandableListAdapter());

		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String title = "";
				if (groupPosition == 0) {
					title = myTs.get(childPosition);
				} else {
					title = fpyTs.get(childPosition);
				}
				Toast.makeText(getApplicationContext(), title,
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private class MyExpandableListAdapter extends BaseExpandableListAdapter {

		/**
		 * ��ȡָ����ĳ��ȣ���ָ�����ж��ٸ����б���
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			int total = 0;
			if (groupPosition == 0) {
				total = myTs.size();
			} else {
				total = fpyTs.size();
			}
			return total;
		}

		/**
		 * ����ָ����λ�ã����б����λ��
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * ���ָ����λ�ã�ָ�����б�������б����е�����
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String title = "";
			if (groupPosition == 0) {
				title = myTs.get(childPosition);
			} else {
				title = fpyTs.get(childPosition);
			}
			return title;
		}

		/**
		 * ����ָ����λ�ã�ָ�����б�������
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.type_tasks, null);
			TextView task_title = (TextView) view.findViewById(R.id.task_title);
			task_title.setText(getChild(groupPosition, childPosition)
					.toString());
			return view;
		}

		/**
		 * �����ĸ���
		 */
		@Override
		public int getGroupCount() {
			return tasks.size();
		}

		/**
		 * ����ָ����λ��
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * ����ָ�����б����е�����
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return tasks.get(groupPosition);
		}

		/**
		 * �������б�������
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.types, null);
			TextView group_title = (TextView) view
					.findViewById(R.id.group_title);
			group_title.setText(getGroup(groupPosition).toString());
			group_title.setTextSize(20);
			ImageView group_img = (ImageView) view.findViewById(R.id.group_img);
			if (isExpanded) {
				group_img.setImageResource(R.drawable.arrow_top);// ��ͷ����
			} else {// �۵�
				group_img.setImageResource(R.drawable.arrow_bottom);// ��ͷ����
			}

			return view;
		}

		/**
		 * �ж��Ƿ��ȶ�
		 */
		@Override
		public boolean hasStableIds() {
			return false;
		}

		/**
		 * ��ѡ���ӽڵ�ʱ����
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

}
