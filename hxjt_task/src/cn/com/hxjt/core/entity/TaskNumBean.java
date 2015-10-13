package cn.com.hxjt.core.entity;

/**
 * TODO 我的首页数据bean
 * 
 * @author pang
 *
 */
public class TaskNumBean {

	// 展示名字
	private String display;
	// 名字类型，用来http请求参数
	private String type;
	// 具体任务数量
	private String num;

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

}
