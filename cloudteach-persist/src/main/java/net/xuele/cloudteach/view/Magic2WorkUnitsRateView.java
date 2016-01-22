package net.xuele.cloudteach.view;

/**
 * Magic2WorkUnitsRateView
 * 按课程统计
 * @author panglx
 * @date on 2015/12/23 0023.
 */
public class Magic2WorkUnitsRateView {
	/**
	 * 课程id
	 */
	private String unitId;

	/**
	 * 按课程统计百分比
	 */
	private Double rate;

	/**
	 * 按课程统计分数等级
	 */
	private int score;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
