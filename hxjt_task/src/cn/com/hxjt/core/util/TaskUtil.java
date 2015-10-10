package cn.com.hxjt.core.util;

import org.json.JSONException;
import org.json.JSONObject;

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
}
