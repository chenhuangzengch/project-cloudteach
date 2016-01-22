package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * SRTeacherWorkStuDTO
 * 教师布置作业的学生信息
 * @author duzg
 * @date on 2015/9/29 0017.
 */
public class SRTeacherWorkStuDTO implements Serializable {


    private static final long serialVersionUID = -8529601995262264370L;
    /**
     * 学生ID
     */
    private String studentId = "";

    /**
     * 学生名字
     */
    private String studentName = "";

    /**
     * 学生头像fileKey
     */
    private String headUrl = "";


    /**
     * 作业得分
     */
    private int score = 0;

    /**
     * 成绩排名
     */
    private int orderNum = 0;

    /**
     * 挑战次数（用于提分宝作业）
     */
    private int challengeTimes = 0;

    /**
     * 提交状态
     */
    private int subStatus = 0;

    /**
     * 批改状态
     */
    private int correctStatus = 0;

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getChallengeTimes() {
        return challengeTimes;
    }

    public void setChallengeTimes(int challengeTimes) {
        this.challengeTimes = challengeTimes;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public int getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(int correctStatus) {
        this.correctStatus = correctStatus;
    }

    @Override
    public String toString() {
        return "TeacherWorkStatisticsStuDTO{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", score=" + score +
                ", orderNum=" + orderNum +
                ", challengeTimes=" + challengeTimes +
                ", subStatus=" + subStatus +
                ", correctStatus=" + correctStatus +
                '}';
    }
}
