package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtBankItemShare;

/**
 * BankItemShareView
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemShareView extends CtBankItemShare{
	/**
	 * 收藏次数
	 */
	private Integer collectTimes;

	/**
	 * 点赞次数
	 */
	private Integer praiseTimes;

	/**
	 * 头像
	 */
	private String icon;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
