package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/7/22 0022.
 */
public class StudentWorkCommunicationView {

    private int me;
    private int praiseStatus;
    private String answerId;
    private String userId;
    private String studentHeadIcon;
    private String studentName;
    private String classInfo;
    private Date subTime;
    private String answerContext;
    private int score;
    private int sysScore;
    private String scoreContext;

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public int getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(int praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public String getScoreContext() {
        return scoreContext;
    }

    public void setScoreContext(String scoreContext) {
        this.scoreContext = scoreContext;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudentHeadIcon() {
        return studentHeadIcon;
    }

    public void setStudentHeadIcon(String studentHeadIcon) {
        this.studentHeadIcon = studentHeadIcon;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSysScore() {
        return sysScore;
    }

    public void setSysScore(int sysScore) {
        this.sysScore = sysScore;
    }
}
