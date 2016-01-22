package net.xuele.cloudteach.view;

import java.util.Date;

/**
 * MyStatisticsView
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkMyStatisticsView {

	/**
	 * 练习id
	 */
	private String practiceId;

	/**
	 * 最高挑战等级
	 */
	private int score;

	/**
	 * 最高成绩描述
	 */
	private String scoreContext;

	/**
	 * 一次练习中挑战次数
	 */
	private int times;

	/**
	 * 练习时长
	 */
	private long pTime;

	/**
	 * 最佳挑战时长
	 */
	private long cTime;

	/**
	 * 正确题数
	 */
	private int rNum;

	/**
	 * 总题数
	 */
	private int tNum;

	/**
	 * 练习开始时间
	 */
	private Date bTime;

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getScoreContext() {
		return scoreContext;
	}

	public void setScoreContext(String scoreContext) {
		this.scoreContext = scoreContext;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public long getpTime() {
		return pTime;
	}

	public void setpTime(long pTime) {
		this.pTime = pTime;
	}

	public long getcTime() {
		return cTime;
	}

	public void setcTime(long cTime) {
		this.cTime = cTime;
	}

	public int getrNum() {
		return rNum;
	}

	public void setrNum(int rNum) {
		this.rNum = rNum;
	}

	public int gettNum() {
		return tNum;
	}

	public void settNum(int tNum) {
		this.tNum = tNum;
	}

	public Date getbTime() {
		return bTime;
	}

	public void setbTime(Date bTime) {
		this.bTime = bTime;
	}
}
