package net.xuele.cloudteach.web.wrapper;

/**
 * Created by panglx on 2015/7/2 0002.
 */
public class CloudShareCollectWrapper {
	/**
	 * 分享ID
	 */
	private String diskId;

	/**
	 * 分享状态 add by panglx 2015/7/2
	 * 0--未分享
	 * 1--已分享
	 */
	private int shareState;

	public int getShareState() {
		return shareState;
	}

	public void setShareState(int shareState) {
		this.shareState = shareState;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}
}
