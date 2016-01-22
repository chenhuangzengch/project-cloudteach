package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * StudentStatisticsDTO
 *  班级统计中学生统计类
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class StudentStatisticsDTO implements Serializable{
	private static final long serialVersionUID = -4512062302211739004L;
	/**
	 * 学生id
	 */
	private String userId;

	/**
	 * 学生名称
	 */
	private String userName;

	/**
	 * 最高挑战成绩
	 */
	private int score;

	/**
	 * 最高挑战成绩描述
	 */
	private String scoreContext;

	/**
	 * 挑战次数
	 */
	private int times;

	/**
	 * 总挑战时长
	 */
	private String pTime;

	/**
	 * 平均挑战时长
	 */
	private String avgTime;

	/**
	 * 正确率
	 */
	private String rate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
