package net.xuele.cloudteach.web.wrapper;

/**
 * 点赞/取消赞返回值
 * Created by panglx on 2015/7/2 0002.
 */
public class SharePraiseWrapper {
	/**
	 * 分享ID
	 */
	private String shareId;

	/**
	 * 点赞状态
	 * 0--未赞
	 * 1--已赞
	 */
	private Integer praiseState;

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public Integer getPraiseState() {
		return praiseState;
	}

	public void setPraiseState(Integer praiseState) {
		this.praiseState = praiseState;
	}
}
