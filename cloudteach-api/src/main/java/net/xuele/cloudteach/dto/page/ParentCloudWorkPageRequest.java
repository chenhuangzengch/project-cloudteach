package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * Created by dj on 2015/7/31 0031.
 */
public class ParentCloudWorkPageRequest extends StudentWorkPageRequest implements Serializable {
    private long publishTime;

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

    //家长id
    private String userId;

    @Override
    public String getPublishTime() {
        return super.getPublishTime();
    }

    @Override
    public void setPublishTime(String publishTime) {
        super.setPublishTime(publishTime);
    }

    @Override
    public String getSubjectId() {
        return subjectId;
    }

    @Override
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int getWorkType() {
        return workType;
    }

    @Override
    public void setWorkType(int workType) {
        this.workType = workType;
    }

    @Override
    public String getSchoolId() {
        return schoolId;
    }

    @Override
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getStudentName() {
        return studentName;
    }

    @Override
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String getStudentHeadIcon() {
        return studentHeadIcon;
    }

    @Override
    public void setStudentHeadIcon(String studentHeadIcon) {
        this.studentHeadIcon = studentHeadIcon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StudentWorkPageRequest{" +
                "subjectId='" + subjectId + '\'' +
                ", workType=" + workType +
                ", schoolId='" + schoolId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentHeadIcon='" + studentHeadIcon + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

}
