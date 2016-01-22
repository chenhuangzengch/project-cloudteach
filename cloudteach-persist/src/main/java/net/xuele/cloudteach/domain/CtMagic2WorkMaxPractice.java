package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtMagic2WorkMaxPractice {
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
        this.practiceId = practiceId == null ? null : practiceId.trim();
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
        this.userId = userId == null ? null : userId.trim();
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
        this.maxScoreContext = maxScoreContext == null ? null : maxScoreContext.trim();
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
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}