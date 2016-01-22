package net.xuele.cloudteach.view;

import java.io.Serializable;

/**
 * Created by hujx on 2015/12/1 0001.
 */
public class LearningInfoStuSynclassWorkView implements Serializable {

    /* 学生ID */
    private String studentId;
    /* 学生姓名 */
    private String studentName;
    /* 学生头像 */
    private String studentIcon;
    /* 游戏获得分数 */
    private Integer score;
    /* 游戏次数 */
    private Integer playFreq;
    /* 游戏练习时间*/
    private Integer playTime;
    /* 作业ID */
    private String workId;
    /* 同步课堂游戏PLAY_ID*/
    private String workGameId;
    /* 学校ID */
    private String schoolId;
    /* 提交状态 */
    private Integer subStatus;

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getWorkGameId() {
        return workGameId;
    }

    public void setWorkGameId(String workGameId) {
        this.workGameId = workGameId;
    }

    public Integer getPlayFreq() {
        return playFreq;
    }

    public void setPlayFreq(Integer playFreq) {
        this.playFreq = playFreq;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

}
