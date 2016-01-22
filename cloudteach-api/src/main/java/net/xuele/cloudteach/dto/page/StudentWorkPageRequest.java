package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * StudentWorkPageRequest
 *
 * @author hujx
 * @date 2015/7/10 0002
 */
public class StudentWorkPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 506671167100186467L;

    private String publishTime;

    // 科目名称
    private String subjectId;

    // 作业类型
    private int workType;

    // 学校ID
    private String schoolId;

    // 学生ID
    private String studentId;

    // 学生姓名
    private String studentName;

    // 学生头像
    private String studentHeadIcon;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "StudentWorkPageRequest{" +
                "publishTime='" + publishTime + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", workType=" + workType +
                ", schoolId='" + schoolId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentHeadIcon='" + studentHeadIcon + '\'' +
                '}';
    }
}
