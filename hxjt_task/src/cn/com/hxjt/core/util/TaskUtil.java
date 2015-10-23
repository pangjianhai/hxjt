package cn.com.hxjt.core.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import cn.com.hxjt.core.R;
import cn.com.hxjt.core.entity.TaskBean;

/**
 * TODO 任务工具类
 * 
 * @author pang
 *
 */
public class TaskUtil {

	public static TaskBean getBeanByJson(String data) {
		TaskBean tb = new TaskBean();
		try {
			JSONObject j = new JSONObject(data);
			String id = j.getString("ID");
			String name = j.getString("Name");
			String pro = j.getString("Project");
			String proPosition = j.getString("ProjectPosition");
			String runningState = j.getString("RunningState");
			String arranger = j.getString("Arranger");
			String receiver = j.getString("Receiver");
			String creator = j.getString("Creator");
			String creationDate = j.getString("CreationDate");
			String modifyDate = j.getString("ModifyDate");
			String completionDate = j.getString("CompletionDate");
			String importantLevel = j.getString("ImportantLevel");
			String emergentLevel = j.getString("EmergentLevel");
			String signDate = j.getString("SignDate");
			String requiredCompletionDate = j
					.getString("RequiredCompletionDate");
			boolean isNeedCheck = j.getBoolean("IsNeedCheck");

			tb.setId(id);
			tb.setName(name);
			tb.setPro(pro);
			tb.setProPosition(proPosition);
			tb.setRunningState(runningState);
			tb.setArranger(arranger);
			tb.setReceiver(receiver);
			tb.setCreator(creator);
			tb.setCreateDate(creationDate);
			tb.setModifyDate(modifyDate);
			tb.setCompleteDate(completionDate);
			tb.setImportantLevel(importantLevel);
			tb.setEmergentLevel(emergentLevel);
			tb.setSignDate(signDate);
			tb.setRequireCompleteDate(requiredCompletionDate);
			tb.setNeedCheck(isNeedCheck);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tb;
	}

	public static void no_login_alter(View v, final Activity ctx) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View noLoginAlter = inflater.inflate(R.layout.app_nologin_alter,
				null, false);
		final PopupWindow popWindow = new PopupWindow(noLoginAlter, 400,
				WindowManager.LayoutParams.WRAP_CONTENT, true);
		ImageView close_nologin_alert_image = (ImageView) noLoginAlter
				.findViewById(R.id.close_nologin_alert_image);
		close_nologin_alert_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow.dismiss();
			}
		});

		Button alert_login = (Button) noLoginAlter
				.findViewById(R.id.alert_login);
		alert_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow.dismiss();
			}

		});
		Button alert_reg = (Button) noLoginAlter.findViewById(R.id.alert_reg);
		alert_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow.dismiss();
			}

		});
		popWindow.setFocusable(true);
		backgroundAlpha(0.7f, ctx);
		popWindow.setOnDismissListener(new PoponDismissListener(ctx));
		popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
	}

	public static void backgroundAlpha(float bgAlpha, Activity ctx) {
		WindowManager.LayoutParams lp = ctx.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		ctx.getWindow().setAttributes(lp);
	}

	static class PoponDismissListener implements PopupWindow.OnDismissListener {

		private Activity ctx;

		public static PoponDismissListener newInstance(Activity ctx) {
			return new PoponDismissListener(ctx);
		}

		public PoponDismissListener(Activity ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public void onDismiss() {
			backgroundAlpha(1f, ctx);
		}

	}
}
