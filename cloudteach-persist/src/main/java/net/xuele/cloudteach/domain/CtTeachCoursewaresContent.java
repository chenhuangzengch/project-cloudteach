package net.xuele.cloudteach.domain;

public class CtTeachCoursewaresContent {
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 内容所属对象:1我的课件 2分享的课件 3课件发布课件
     */
    private Integer belongType;

    /**
     * 课件内容
     */
    private String content;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 获取 [CT_TEACH_COURSEWARES_CONTENT] 的属性 授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_CONTENT]的属性授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_CONTENT] 的属性 内容所属对象:1我的课件 2分享的课件 3课件发布课件
     */
    public Integer getBelongType() {
        return belongType;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_CONTENT]的属性内容所属对象:1我的课件 2分享的课件 3课件发布课件
     */
    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_CONTENT] 的属性 课件内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_CONTENT]的属性课件内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}