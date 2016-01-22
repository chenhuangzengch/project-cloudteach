package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Magic2WorkMyStatisticsDTO
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkMyStatisticsDTO implements Serializable{

	private static final long serialVersionUID = -2489411224134233939L;
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
	private String pTime;

	/**
	 * 最佳挑战时长
	 */
	private String cTime;

	/**
	 * 最佳挑战正确率
	 */
	private String rate;

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

	public String getpTime() {
		return pTime;
	}

	public void setpTime(String pTime) {
		this.pTime = pTime;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getbTime() {
		return bTime;
	}

	public void setbTime(Date bTime) {
		this.bTime = bTime;
	}
}
