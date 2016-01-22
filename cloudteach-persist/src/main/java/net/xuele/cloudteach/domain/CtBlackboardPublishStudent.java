package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtBlackboardPublishStudent {
    /**
     * 板书发布学生ID
     */
    private String blackboardUserId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 板书ID
     */
    private String blackboardId;

    /**
     * 查看状态
     */
    private Integer readStatus;

    /**
     * 查看时间
     */
    private Date readTime;

    /**
     * 状态:0删除 1有效
     */
    private Integer status;

    private  String schoolId;

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 板书发布学生ID
     */
    public String getBlackboardUserId() {
        return blackboardUserId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性板书发布学生ID
     */
    public void setBlackboardUserId(String blackboardUserId) {
        this.blackboardUserId = blackboardUserId == null ? null : blackboardUserId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 学生用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性学生用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 板书ID
     */
    public String getBlackboardId() {
        return blackboardId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性板书ID
     */
    public void setBlackboardId(String blackboardId) {
        this.blackboardId = blackboardId == null ? null : blackboardId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 查看状态
     */
    public Integer getReadStatus() {
        return readStatus;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性查看状态
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 查看时间
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性查看时间
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_STUDENT] 的属性 状态:0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_STUDENT]的属性状态:0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}