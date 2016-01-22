package net.xuele.cloudteach.web.wrapper;

/**
 * TeachCoursewaresStickyWrapper
 *
 * @author sunxh
 * @date 15/8/4
 */
public class TeachCoursewaresStickyWrapper {

    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 置顶标志：0否 1是
     */
    private Integer stickStatus;

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }
}
