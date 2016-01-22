package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class CourseReappearStudentDTO implements Serializable {
    private static final long serialVersionUID = 3557676318358515737L;
    /**
     * uuid
     */
    private String rsId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 课件发布ID
     */
    private String reappearId;

    /**
     * 查看状态
     */
    private Integer readStatus;

    /**
     * 查看时间
     */
    private Date readTime;

    /**
     * 状态:0删除 1有效
     */
    private Integer status;

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 uuid
     */
    public String getRsId() {
        return rsId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性uuid
     */
    public void setRsId(String rsId) {
        this.rsId = rsId == null ? null : rsId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 学生用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性学生用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 课件发布ID
     */
    public String getReappearId() {
        return reappearId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性课件发布ID
     */
    public void setReappearId(String reappearId) {
        this.reappearId = reappearId == null ? null : reappearId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 查看状态
     */
    public Integer getReadStatus() {
        return readStatus;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性查看状态
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 查看时间
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性查看时间
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_STUDENT] 的属性 状态:0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_STUDENT]的属性状态:0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseReappearStudentDTO{" +
                "rsId='" + rsId + '\'' +
                ", userId='" + userId + '\'' +
                ", classId='" + classId + '\'' +
                ", reappearId='" + reappearId + '\'' +
                ", readStatus=" + readStatus +
                ", readTime=" + readTime +
                ", status=" + status +
                '}';
    }
}