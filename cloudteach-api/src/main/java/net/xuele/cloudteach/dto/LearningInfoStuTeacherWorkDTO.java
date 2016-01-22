package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情作业学生教师作业信息
 */
public class LearningInfoStuTeacherWorkDTO implements Serializable {
    private static final long serialVersionUID = 2730660385408181117L;

    /* 学生ID */
    private String studentId;
    /* 学生姓名 */
    private String studentName;
    /* 学生头像 */
    private String studentIcon;
    /* 提交状态 */
    private Integer subStatus;
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
    /* 口语作业系统评分*/
    private Integer sysScore;
    /* 作业类型 1:预习，4:电子 */
    private Integer workType;

    public Integer getSysScore() {
        return sysScore;
    }

    public void setSysScore(Integer sysScore) {
        this.sysScore = sysScore;
    }

    public Integer getVoicePlayFreq() {
        return voicePlayFreq;
    }

    public void setVoicePlayFreq(Integer voicePlayFreq) {
        this.voicePlayFreq = voicePlayFreq;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
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


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    @Override
    public String toString() {
        return "LearningInfoStudentDTO{" +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentIcon='" + studentIcon + '\'' +
                ", subStatus=" + subStatus +
                ", beginTime=" + beginTime +
                ", subTime=" + subTime +
                ", commentOthersTimes=" + commentOthersTimes +
                ", othersPraiseTimes=" + othersPraiseTimes +
                ", workType=" + workType +
                '}';
    }
}
