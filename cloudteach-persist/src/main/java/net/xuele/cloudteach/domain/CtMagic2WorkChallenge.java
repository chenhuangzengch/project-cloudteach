package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtMagic2WorkChallenge {
    private String challengeId;

    private String unitId;

    private String practiceId;

    private String userId;

    private Integer sort;

    private Date beginTime;

    private Date endTime;

    private Integer totalQuenum;

    private Integer rightQuenum;

    private Integer score;

    private String scoreContext;

    private Integer shareStatus;

    private Date shareTime;

    private String schoolId;

    private Integer status;

    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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