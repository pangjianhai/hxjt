package cn.com.hxjt.core.entity;

/**
 * @todo 任务类型entity
 * @author pang
 *
 */
public class TaskTypeEntity {
	public static final int TYPE_MINE = 0;// 我的
	public static final int TYPE_ASSIGNE = 1;// 我分配的

	public static final int PROGRESS_DONE = 0;// 已经完成的
	public static final int PROGRESS_WILL_DO = 1;// 将要做的
	public static final int PROGRESS_RUNNING = 2;// 正在做的
	public static final int PROGRESS_NOT_DONE = 4;// 逾期的

	/**
	 * 类型
	 */
	private int type;

	/**
	 * 进度
	 */
	private int progress;
	/**
	 * 标题
	 */
	private String title;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
