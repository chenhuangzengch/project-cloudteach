package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Magic2WorkMaxPracticeDTO
 *
 * @author panglx
 * @date on 2015/10/27 0027.
 */
public class Magic2WorkMaxPracticeDTO implements Serializable{

	private static final long serialVersionUID = 7770289244189846995L;

	private String practiceId;

	private String unitId;

	private String userId;

	private String challengeId;

	private Integer maxScore;

	private String maxScoreContext;

	private Date lastTime;

	private Integer sort;

	private String schoolId;

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public String getMaxScoreContext() {
		return maxScoreContext;
	}

	public void setMaxScoreContext(String maxScoreContext) {
		this.maxScoreContext = maxScoreContext;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
}
