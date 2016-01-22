package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class CourseReappearCoursewaresDTO implements Serializable {
    private static final long serialVersionUID = -250295831580072336L;
    /**
     * 课件发布课件ID
     */
    private String rcId;
    /**
     * 课件发布ID
     */
    private String reappearId;

    private String schoolId;
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 课件名称
     */
    private String title;

    /**
     * 状态:0删除  1有效
     */
    private Integer status;

    /**
     * 获取 [CT_COURSE_REAPPEAR_COURSEWARES] 的属性 课件发布课件ID
     */
    public String getRcId() {
        return rcId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_COURSEWARES]的属性课件发布课件ID
     */
    public void setRcId(String rcId) {
        this.rcId = rcId == null ? null : rcId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_COURSEWARES] 的属性 授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_COURSEWARES]的属性授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_COURSEWARES] 的属性 课件名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_COURSEWARES]的属性课件名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_COURSEWARES] 的属性 状态:0删除  1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_COURSEWARES]的属性状态:0删除  1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReappearId() {
        return reappearId;
    }

    public void setReappearId(String reappearId) {
        this.reappearId = reappearId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "CourseReappearCoursewaresDTO{" +
                "rcId='" + rcId + '\'' +
                ", reappearId='" + reappearId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", coursewaresId='" + coursewaresId + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}