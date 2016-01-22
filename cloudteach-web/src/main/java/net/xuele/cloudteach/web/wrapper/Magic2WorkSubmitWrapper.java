package net.xuele.cloudteach.web.wrapper;

/**
 * Magic2WorkSubmitWrapper
 *
 * @author panglx
 * @date on 2015/10/26 0026.
 */
public class Magic2WorkSubmitWrapper {

	private String practiceId;

	private String challengeId;

	private Integer score;

	private String scoreContext;

	private int level;

	private String submitJson;//提交返回json

	public String getSubmitJson() {
		return submitJson;
	}

	public void setSubmitJson(String submitJson) {
		this.submitJson = submitJson;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getScoreContext() {
		return scoreContext;
	}

	public void setScoreContext(String scoreContext) {
		this.scoreContext = scoreContext;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
