package net.xuele.cloudteach.view;

/**
 * ClassStatisticsView
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkClassStatisticsView {
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
	private long pTime;

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

	public long getpTime() {
		return pTime;
	}

	public void setpTime(long pTime) {
		this.pTime = pTime;
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
