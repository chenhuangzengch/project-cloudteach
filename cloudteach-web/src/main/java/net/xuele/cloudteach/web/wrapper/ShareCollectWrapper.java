package net.xuele.cloudteach.web.wrapper;

/**
 * ShareCollectWrapper
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class ShareCollectWrapper {
	/**
	 * 题目ID
	 */
	private String itemId;

	/**
	 * 收藏题目来源id
	 */
	private String pid;

	/**
	 * 收藏状态
	 */
	private int collectState;

	public int getCollectState() {
		return collectState;
	}

	public void setCollectState(int collectState) {
		this.collectState = collectState;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
