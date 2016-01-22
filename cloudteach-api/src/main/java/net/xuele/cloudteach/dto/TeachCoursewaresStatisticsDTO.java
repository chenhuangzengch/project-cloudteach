package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class TeachCoursewaresStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 1579283040911947965L;
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 获取 [CT_TEACH_COURSEWARES_STATISTICS] 的属性 授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_STATISTICS]的属性授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_STATISTICS] 的属性 发布次数
     */
    public Integer getReleases() {
        return releases;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_STATISTICS]的属性发布次数
     */
    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "TeachCoursewaresStatisticsDTO{" +
                "coursewaresId='" + coursewaresId + '\'' +
                ", releases=" + releases +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}