package net.xuele.cloudteach.web.wrapper;

/**
 * TeachCoursewaresShareStatusWrapper
 *
 * @author sunxh
 * @date 15/8/4
 */
public class TeachCoursewaresShareStatusWrapper {
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 状态:0删除  1有效
     */
    private Integer shareStatus;

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }
}
