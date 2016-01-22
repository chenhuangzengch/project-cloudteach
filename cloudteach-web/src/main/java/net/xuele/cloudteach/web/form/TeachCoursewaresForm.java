package net.xuele.cloudteach.web.form;

/**
 * TeachCoursewaresForm
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */
public class TeachCoursewaresForm {

    /**
     * 用户ID:（我的课件所属人）userid
     */
    private String creator;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 课件名称
     */
    private String title;

    /**
     * 课件内容
     */
    private String content;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
