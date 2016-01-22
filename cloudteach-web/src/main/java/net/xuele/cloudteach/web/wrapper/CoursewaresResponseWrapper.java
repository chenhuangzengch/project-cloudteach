package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class CoursewaresResponseWrapper {
    private String state;
    private String coursewaresId;
    private String coursewaresName;
    private String content;
    private String userId;
    private String unitId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }

    public String getCoursewaresName() {
        return coursewaresName;
    }

    public void setCoursewaresName(String coursewaresName) {
        this.coursewaresName = coursewaresName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
