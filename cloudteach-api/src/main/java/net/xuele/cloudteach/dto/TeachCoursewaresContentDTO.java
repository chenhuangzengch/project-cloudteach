package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class TeachCoursewaresContentDTO implements Serializable {
    private static final long serialVersionUID = 4165782795772780390L;
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
    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

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

    @Override
    public String toString() {
        return "TeachCoursewaresContentDTO{" +
                "coursewaresId='" + coursewaresId + '\'' +
                ", belongType=" + belongType +
                ", content='" + content + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}