package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtCloudDiskShare;

/**
 * Created by panglx on 2015/7/7 0007.
 */
public class CtCloudDiskShareView extends CtCloudDiskShare {
	/**
	 * 收藏数
	 */
	private Integer saveTimes;

	/**
	 * 点赞总数
	 */
	private Integer praiseTimes;

	/**
	 * 收藏状态
	 */
	private Integer collectState;

	/**
	 * 点赞状态
	 */
	private Integer praiseState;

	/**
	 * 分享状态--是否自己分享
	 */
	private Integer shareState;

	public Integer getShareState() {
		return shareState;
	}

	public void setShareState(Integer shareState) {
		this.shareState = shareState;
	}

	public Integer getSaveTimes() {
		return saveTimes;
	}

	public void setSaveTimes(Integer saveTimes) {
		this.saveTimes = saveTimes;
	}

	public Integer getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(Integer praiseTimes) {
		this.praiseTimes = praiseTimes;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public Integer getPraiseState() {
		return praiseState;
	}

	public void setPraiseState(Integer praiseState) {
		this.praiseState = praiseState;
	}
}
