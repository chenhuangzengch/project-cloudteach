package net.xuele.cloudteach.view;

/**
 * Magic2WorkBookRateView
 * 按课本统计
 * @author panglx
 * @date on 2015/12/23 0023.
 */
public class Magic2WorkBookRateView {
	/**
	 * 按课本统计百分比
	 */
	private Double rate;

	/**
	 * 按课本统计分数等级
	 */
	private int score;

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
