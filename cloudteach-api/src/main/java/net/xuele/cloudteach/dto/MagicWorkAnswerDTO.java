package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * MagicWorkAnswerDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkAnswerDTO implements Serializable {
    private static final long serialVersionUID = -8689661525812907064L;
    private String answerId;

    private String workId;

    private String userId;

    private String classId;

    private String context;

    private Integer score;

    private String challengeId;
    /**
     * 0未提交，1已提交
     */
    private Integer subStatus;

    /**
     * 0未批改，1已批改
     */
    private Integer correctStatus;

    private Date subTime;

    private Date correctTime;

    private Date updateTime;

    private String updateUserId;

    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER] 的属性 0未提交，1已提交
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER]的属性0未提交，1已提交
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER] 的属性 0未批改，1已批改
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER]的属性0未批改，1已批改
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public Date getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkAnswerDTO{" +
                "answerId='" + answerId + '\'' +
                ", workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", classId='" + classId + '\'' +
                ", context='" + context + '\'' +
                ", score=" + score +
                ", challengeId='" + challengeId + '\'' +
                ", subStatus=" + subStatus +
                ", correctStatus=" + correctStatus +
                ", subTime=" + subTime +
                ", correctTime=" + correctTime +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}