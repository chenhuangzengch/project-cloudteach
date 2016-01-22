package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情作业同步课堂学生作业信息
 */
public class LearningInfoStuSynclassWorkDTO implements Serializable {
    private static final long serialVersionUID = 3812442539604994012L;

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
    private long playTime;

    public Integer getPlayFreq() {
        return playFreq;
    }

    public void setPlayFreq(Integer playFreq) {
        this.playFreq = playFreq;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentIcon() {
        return studentIcon;
    }

    public void setStudentIcon(String studentIcon) {
        this.studentIcon = studentIcon;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    @Override
    public String toString() {
        return "LearningInfoStuSynclassWorkDTO{" +
                "playFreq=" + playFreq +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentIcon='" + studentIcon + '\'' +
                ", score=" + score +
                ", playTime=" + playTime +
                '}';
    }
}
