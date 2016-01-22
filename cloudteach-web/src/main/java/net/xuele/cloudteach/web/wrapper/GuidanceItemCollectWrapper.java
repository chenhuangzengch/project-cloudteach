package net.xuele.cloudteach.web.wrapper;

/**
 * Created by panglx on 2015/7/16 0016.
 */
public class GuidanceItemCollectWrapper {
	/**
	 * 预习项ID
	 */
	private String itemId;

	/**
	 * 收藏预习项来源id
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
