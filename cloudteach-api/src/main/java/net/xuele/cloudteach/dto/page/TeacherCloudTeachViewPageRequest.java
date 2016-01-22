package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

public class TeacherCloudTeachViewPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -743440353920930708L;
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
     * 学校ID
     */
    private String schoolId;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 是否只获取作业信息:0：包括板书、网络课件 1：不包括板书、网络课件)
     */
    private Integer onlyWork;

    /**
     * 列表类型：1其他  2教师云教学首页
     */
    private int listType;

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getOnlyWork() {
        return onlyWork;
    }

    public void setOnlyWork(Integer onlyWork) {
        this.onlyWork = onlyWork;
    }

    @Override
    public String toString() {
        return "TeacherCloudTeachViewPageRequest{" +
                "workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", classId='" + classId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", onlyWork=" + onlyWork +
                '}';
    }
}