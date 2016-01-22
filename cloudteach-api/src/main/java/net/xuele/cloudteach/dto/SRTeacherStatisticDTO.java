package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by cm.wang on 2015/8/11 0011.
 */
public class SRTeacherStatisticDTO implements Serializable{

    private static final long serialVersionUID = 595440079906176632L;

    private Integer rankNum;

    private String schoolName;

    private String schoolId;

    private String teacherId;

    private String teacherName;

    //科目名称
    private String subjectName;

    //作业数量
    private Integer workNum;

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getWorkNum() {
        return workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
