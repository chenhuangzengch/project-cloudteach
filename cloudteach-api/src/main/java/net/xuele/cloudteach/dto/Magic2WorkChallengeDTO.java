package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Magic2WorkChallengeDTO
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2WorkChallengeDTO implements Serializable{

	private static final long serialVersionUID = 5206275561047714151L;

	private String challengeId;//挑战id

	private String unitId;//课程id

	private String practiceId;//练习id

	private String userId;//用户id

	private Integer sort;//第几套题

	private Date beginTime;//开始答题时间

	private Date endTime;//结束答题时间

	private Integer totalQuenum;//总题数

	private Integer rightQuenum;//正确题数

	private Integer score;//挑战成绩

	private String scoreContext;//成绩描述

	private Integer shareStatus;//分享状态

	private Date shareTime;//分享时间

	private String schoolId;//学校id

	private Integer status;//状态 0-删除，1-正常

	private int level;//建议级别

	private String submitJson;//提交返回json

	public String getSubmitJson() {
		return submitJson;
	}

	public void setSubmitJson(String submitJson) {
		this.submitJson = submitJson;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId == null ? null : challengeId.trim();
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTotalQuenum() {
		return totalQuenum;
	}

	public void setTotalQuenum(Integer totalQuenum) {
		this.totalQuenum = totalQuenum;
	}

	public Integer getRightQuenum() {
		return rightQuenum;
	}

	public void setRightQuenum(Integer rightQuenum) {
		this.rightQuenum = rightQuenum;
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
		this.scoreContext = scoreContext == null ? null : scoreContext.trim();
	}

	public Integer getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(Integer shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId == null ? null : schoolId.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
