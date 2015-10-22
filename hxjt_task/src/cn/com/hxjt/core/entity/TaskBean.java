package cn.com.hxjt.core.entity;

/**
 * @todo 任务task
 * @author pang
 *
 */
public class TaskBean {

	private String id;
	private String name;
	private String projectType;
	private String pro;
	private String proPosition;
	private String runningState;
	private String arranger;
	private String receiver;
	private String creator;
	private String createDate;
	private String modifyDate;
	private String completeDate;
	private String importantLevel;
	private String emergentLevel;
	private String signDate;
	private String requireCompleteDate;
	private boolean isNeedCheck;

	public String getId() {
		return id;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getProPosition() {
		return proPosition;
	}

	public void setProPosition(String proPosition) {
		this.proPosition = proPosition;
	}

	public String getRunningState() {
		return runningState;
	}

	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}

	public String getArranger() {
		return arranger;
	}

	public void setArranger(String arranger) {
		this.arranger = arranger;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getEmergentLevel() {
		return emergentLevel;
	}

	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}

	public String getRequireCompleteDate() {
		return requireCompleteDate;
	}

	public void setRequireCompleteDate(String requireCompleteDate) {
		this.requireCompleteDate = requireCompleteDate;
	}

	public boolean isNeedCheck() {
		return isNeedCheck;
	}

	public void setNeedCheck(boolean isNeedCheck) {
		this.isNeedCheck = isNeedCheck;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

}
