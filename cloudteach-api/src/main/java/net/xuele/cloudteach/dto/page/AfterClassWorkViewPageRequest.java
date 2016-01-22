package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

public class AfterClassWorkViewPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -4207843464508333368L;
    /**
     * 作业状态类型 1进行中 2定时未发布 3已归档
     */
    private int workStatusType;
    /**
     * 作业号
     */
    private String workId;
    /**
     * 布置作业教师用户号
     */
    private String userId;
    /**
     * 科目号
     */
    private String subjectId;
    /**
     * 课程号
     */
    private String unitId;
    /**
     * 班级号
     */
    private String classId;
    /**
     * 作业类型
     */
    private String workType;
    /**
     * 学校ID
     */
    private String schoolId;
    /**
     * 发布时间
     */
    private String publishTime;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getWorkStatusType() {
        return workStatusType;
    }

    public void setWorkStatusType(int workStatusType) {
        this.workStatusType = workStatusType;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "AfterClassWorkViewPageRequest{" +
                "workStatusType=" + workStatusType +
                ", workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", classId='" + classId + '\'' +
                ", workType='" + workType + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}