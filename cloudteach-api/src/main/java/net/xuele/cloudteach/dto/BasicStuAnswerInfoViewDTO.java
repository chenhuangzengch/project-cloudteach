package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/8/8 0008.
 * 学生回答作业通用
 */
public class BasicStuAnswerInfoViewDTO implements Serializable {

    private static final long serialVersionUID = -523251603974571189L;

    // 学生名字
    private String studentName;

    // 学生头像
    private String studentHeadIcon;

    // 学生提交作业时间
    private Date submitTime;

    // 学生作业回答
    private String answerContext;

    // 提交状态
    private int subStatus;

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    @Override
    public String toString() {
        return "BasicStuAnswerInfoViewDTO{" +
                "studentName='" + studentName + '\'' +
                ", studentHeadIcon='" + studentHeadIcon + '\'' +
                ", submitTime=" + submitTime +
                ", answerContext='" + answerContext + '\'' +
                ", subStatus=" + subStatus +
                '}';
    }
}
