package cn.com.hxjt.core.util;

/**
 * @todo 附件操作
 * @author pang
 *
 */
public interface IAttachmentOps {

	/**
	 * 
	 * @param position
	 * @user:pang
	 * @data:2015年10月14日
	 * @todo:删除附件
	 * @return:void
	 */
	public void delAtt(String path);

	/**
	 * 
	 * @param attId
	 * @user:pang
	 * @data:2015年10月14日
	 * @todo:下载附件
	 * @return:void
	 */
	public void download(String attId, String name);
}
