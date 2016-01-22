package net.xuele.cloudteach.web.wrapper;

import java.util.Date;
import java.util.List;

public class CourseReappearWrapper {
    /**
     * 课件发布ID
     */
    private String reappearId;

    /**
     * 发布用户ID
     */
    private String userId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 描述内容
     */
    private String context;

    /**
     * 发布时间
     */
    private Date publishTime;


    /**
     * 课件信息
     */
    private List<TeachCoursewaresWrapper> wrapperList;

    /**
     * 获取 [CT_COURSE_REAPPEAR] 的属性 课件发布ID
     */
    public String getReappearId() {
        return reappearId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR]的属性课件发布ID
     */
    public void setReappearId(String reappearId) {
        this.reappearId = reappearId == null ? null : reappearId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR] 的属性 发布用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR]的属性发布用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    /**
     * 获取 [CT_COURSE_REAPPEAR] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_COURSE_REAPPEAR] 的属性 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置[CT_COURSE_REAPPEAR]的属性发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<TeachCoursewaresWrapper> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<TeachCoursewaresWrapper> wrapperList) {
        this.wrapperList = wrapperList;
    }
}