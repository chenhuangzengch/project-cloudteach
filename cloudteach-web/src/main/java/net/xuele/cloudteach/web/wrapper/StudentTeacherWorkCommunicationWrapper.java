package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.TeacherWorkItemAnswerCommNameViewDTO;
import net.xuele.cloudteach.dto.TeacherWorkItemAnswerFileDTO;
import net.xuele.cloudteach.dto.WorkAnswerCommentViewDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/8/5 0005.
 */
public class StudentTeacherWorkCommunicationWrapper {

    private int me;
    private int praiseStatus;
    private String answerId;
    private String icon;
    private String studentName;
    private String classInfo;
    private Date subTime;
    private String answerContext;
    private int score;
    private int praiseCount;
    private int sysScore;

    // 教师作业(预习，电子，语音)
    private List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList;
    private List<WorkAnswerCommentViewDTO> teacherWorkItemAnswerCommNameViewDTOList;

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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public int getSysScore() {
        return sysScore;
    }

    public void setSysScore(int sysScore) {
        this.sysScore = sysScore;
    }

    public List<WorkAnswerCommentViewDTO> getTeacherWorkItemAnswerCommNameViewDTOList() {
        return teacherWorkItemAnswerCommNameViewDTOList;
    }

    public void setTeacherWorkItemAnswerCommNameViewDTOList(List<WorkAnswerCommentViewDTO> teacherWorkItemAnswerCommNameViewDTOList) {
        this.teacherWorkItemAnswerCommNameViewDTOList = teacherWorkItemAnswerCommNameViewDTOList;
    }

    public List<TeacherWorkItemAnswerFileDTO> getTeacherWorkItemAnswerFileDTOList() {
        return teacherWorkItemAnswerFileDTOList;
    }

    public void setTeacherWorkItemAnswerFileDTOList(List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList) {
        this.teacherWorkItemAnswerFileDTOList = teacherWorkItemAnswerFileDTOList;
    }

}
