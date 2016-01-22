package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Magic2WorkClassTotalDTO
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkClassTotalDTO implements Serializable{

	private static final long serialVersionUID = -3707909073068659316L;

	/**
	 * 总挑战次数
	 */
	private long totalTimes;

	/**
	 * 总挑战时长
	 */
	private String totalTime;

	/**
	 * 平均挑战时长
	 */
	private String avgTime;

	/**
	 * 正确率
	 */
	private String rate;

	public long getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(long totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
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
