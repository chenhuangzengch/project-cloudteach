package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * ScoreRateDTO
 * 班级统计中评价发布百分比
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class ScoreRateDTO implements Serializable{
	private static final long serialVersionUID = 736224926502658486L;
	/**
	 * 分数等级
	 */
	private int score;

	/**
	 * 分数等级描述
	 */
	private String scoreContext;

	/**
	 * 对应学生数
	 */
	private int num;

	/**
	 * 百分比
	 */
	private String rate;

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
