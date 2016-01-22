package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class CourseReappearClassDTO implements Serializable {
    private static final long serialVersionUID = -5543776801962217682L;
    /**
     * 课件发布班级ID
     */
    private String rcId;

    /**
     * 课件发布ID
     */
    private String reappearId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_COURSE_REAPPEAR_CLASS] 的属性 课件发布班级ID
     */
    public String getRcId() {
        return rcId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_CLASS]的属性课件发布班级ID
     */
    public void setRcId(String rcId) {
        this.rcId = rcId == null ? null : rcId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_CLASS] 的属性 课件发布ID
     */
    public String getReappearId() {
        return reappearId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_CLASS]的属性课件发布ID
     */
    public void setReappearId(String reappearId) {
        this.reappearId = reappearId == null ? null : reappearId.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_CLASS] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_CLASS]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR_CLASS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR_CLASS]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseReappearClassDTO{" +
                "rcId='" + rcId + '\'' +
                ", reappearId='" + reappearId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", classId='" + classId + '\'' +
                ", status=" + status +
                '}';
    }
}