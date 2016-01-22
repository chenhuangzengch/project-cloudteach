package net.xuele.cloudteach.view;

/**
 * ClassTotalView
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkClassTotalView {

	/**
	 * 总挑战次数
	 */
	private long totalTimes;

	/**
	 * 总挑战时长
	 */
	private long totalTime;

	/**
	 * 平均挑战时长
	 */
	private long avgTime;

	/**
	 * 正确题数
	 */
	private int rNum;

	/**
	 * 总题数
	 */
	private int tNum;

	public long getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(long totalTimes) {
		this.totalTimes = totalTimes;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(long avgTime) {
		this.avgTime = avgTime;
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
}
