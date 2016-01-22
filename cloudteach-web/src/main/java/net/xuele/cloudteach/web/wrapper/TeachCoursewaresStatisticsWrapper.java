package net.xuele.cloudteach.web.wrapper;

public class TeachCoursewaresStatisticsWrapper {
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 发布次数
     */
    private Integer releases;

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
}