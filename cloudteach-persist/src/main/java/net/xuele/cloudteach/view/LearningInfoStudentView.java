package net.xuele.cloudteach.view;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/12/1 0001.
 */
public class LearningInfoStudentView {

    /* 学生ID */
    private String studentId;
    /* 学生姓名 */
    private String studentName;
    /* 学生头像 */
    private String studentIcon;
    /* 提交状态 */
    private Integer subStatus;
    /* 批改状态 */
    private Integer correctStatus;
    /* 得分 */
    private Integer score;
    /* 口语作业系统评分 */
    private Integer sysScore;
    /* 开始作业时间 */
    private Date beginTime;
    /* 提交作业时间 */
    private Date subTime;
    /* 评论他人作业次数 */
    private Integer commentOthersTimes;
    /* 收到点赞次数 */
    private Integer othersPraiseTimes;
    /* 口语作业练习次数 */
    private Integer voicePlayFreq;
    /* 作业ID */
    private String workId;
    /* 作业类型 1:预习，4:电子 */
    private Integer workType;
    /* 回答ID */
    private String answerId;
    /* 学校ID */
    private String schoolId;
    /* UnitName*/
    private String unitName;

    public Integer getSysScore() {
        return sysScore;
    }

    public void setSysScore(Integer sysScore) {
        this.sysScore = sysScore;
    }

    public Integer getVoicePlayFreq() {
        return voicePlayFreq == null ? 0 : voicePlayFreq;
    }

    public void setVoicePlayFreq(Integer voicePlayFreq) {
        this.voicePlayFreq = voicePlayFreq;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getCommentOthersTimes() {
        return commentOthersTimes;
    }

    public void setCommentOthersTimes(Integer commentOthersTimes) {
        this.commentOthersTimes = commentOthersTimes;
    }

    public Integer getOthersPraiseTimes() {
        return othersPraiseTimes;
    }

    public void setOthersPraiseTimes(Integer othersPraiseTimes) {
        this.othersPraiseTimes = othersPraiseTimes;
    }

    public String getStudentIcon() {
        return studentIcon;
    }

    public void setStudentIcon(String studentIcon) {
        this.studentIcon = studentIcon;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }
}
