package net.xuele.cloudteach.web.wrapper;

/**
 * TeachCoursewaresRemoveWrapper
 *
 * @author sunxh
 * @date 15/8/1
 */
public class TeachCoursewaresRemoveWrapper {

    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 状态:0删除  1有效
     */
    private Integer status;
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }
}
