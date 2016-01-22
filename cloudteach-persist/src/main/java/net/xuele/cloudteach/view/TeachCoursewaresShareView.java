package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtTeachCoursewaresShare;

/**
 * Created by panglx on 2015/7/17 0017.
 */
public class TeachCoursewaresShareView extends CtTeachCoursewaresShare {
	/**
	 * 收藏次数
	 */
	private Integer collectTimes;

	/**
	 * 点赞次数
	 */
	private Integer praiseTimes;

	/**
	 * 收藏状态
	 */
	private int collectState;

	/**
	 * 点赞状态
	 */
	private int praiseState;

	/**
	 * 是否我分享的
	 */
	private int isMyShare;

	public Integer getCollectTimes() {
		return collectTimes;
	}

	public void setCollectTimes(Integer collectTimes) {
		this.collectTimes = collectTimes;
	}

	public Integer getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(Integer praiseTimes) {
		this.praiseTimes = praiseTimes;
	}

	public int getCollectState() {
		return collectState;
	}

	public void setCollectState(int collectState) {
		this.collectState = collectState;
	}

	public int getPraiseState() {
		return praiseState;
	}

	public void setPraiseState(int praiseState) {
		this.praiseState = praiseState;
	}

	public int getIsMyShare() {
		return isMyShare;
	}

	public void setIsMyShare(int isMyShare) {
		this.isMyShare = isMyShare;
	}
}
