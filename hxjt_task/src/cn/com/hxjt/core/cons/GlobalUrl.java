package cn.com.hxjt.core.cons;

/**
 * @todo URL
 * @author pang
 *
 */
public class GlobalUrl {

	public static String IP = "http://222.174.100.181:8998/";

	/**
	 * 获取项目类型
	 */
	public static String getProTypes = "project/GetProjectTypes";

	/**
	 * 获取项目类型岗位
	 */
	public static String getProjectPositions = "project/GetProjectPositions";

	/**
	 * 获取所有的执行人
	 */
	public static String getUsers = "user/GetAllUsers?loginName=zhoumeng";

	/**
	 * 根据项目类型获取项目
	 */
	public static String getProByType = "project/GetProjects";

	/**
	 * 分配任务
	 */
	public static String assignTask = "task/AssignTask";

	/**
	 * 申请任务
	 */
	public static String applyTask = "task/ApplyTask";

	/**
	 * 自建任务
	 */
	public static String createOwnTask = "task/CreateOwnTask";

	/**
	 * 获取任务详情
	 */
	public static String getTaskInfo = "task/GetTaskInfo";
}
