package cn.com.hxjt.core.entity;

/**
 * @todo 任务entity
 * @author pang
 *
 */
public class TaskEntity {

	public static final String TASK_TYPE_ASSIGN = "0";
	public static final String TASK_TYPE_APPLY = "1";
	public static final String TASK_TYPE_CREATE = "2";

	/**
	 * 任务ID
	 */
	private String id;
	/**
	 * 任务标题
	 */
	private String title;

	/**
	 * 执行人ID
	 */
	private String executorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

}
