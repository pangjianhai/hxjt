package cn.com.hxjt.core.cons;

/**
 * @todo URL
 * @author pang
 *
 */
public class GlobalUrl {

	public static String IP = "http://222.174.100.181:8998/";

	/**
	 * 登录
	 */
	public static String login = "user/Login";

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

	// 获取可以执行的操作
	public static String getAvailableOpsByTaskAndUser = "task/GetAvailableOpsByTaskAndUser";
	// 签收
	public static String signForTask = "task/SignForTask";
	// 删除
	public static String cancelTask = "task/CancelTask";
	// 提交
	public static String submitTask = "task/SubmitTask";
	// 接收
	public static String acceptTaskApply = "task/AcceptTaskApply";
	// 审核
	public static String approveTask = "task/ApproveTask";
	/**
	 * 获取任务数量信息
	 */
	public static String getTasksCountInfo = "task/GetTasksCountInfo";

	/**
	 * 获取我的任务总数
	 */
	public static String getMyTask = "task/SearchMyTasks";
	/**
	 * 获取分配任务的相关数目
	 */
	public static String getArrangedTask = "task/SearchTasksAssign";
}
