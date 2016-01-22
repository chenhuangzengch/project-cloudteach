package net.xuele.cloudteach.domain;

public class CtBlackboardPublishClass {
    /**
     * 板书发布班级ID
     */
    private String blackboardClassId;

    /**
     * 板书ID
     */
    private String blackboardId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 状态
     */
    private Integer status;

    private String schoolId;

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_CLASS] 的属性 板书发布班级ID
     */
    public String getBlackboardClassId() {
        return blackboardClassId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_CLASS]的属性板书发布班级ID
     */
    public void setBlackboardClassId(String blackboardClassId) {
        this.blackboardClassId = blackboardClassId == null ? null : blackboardClassId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_CLASS] 的属性 板书ID
     */
    public String getBlackboardId() {
        return blackboardId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_CLASS]的属性板书ID
     */
    public void setBlackboardId(String blackboardId) {
        this.blackboardId = blackboardId == null ? null : blackboardId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_CLASS] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_CLASS]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_CLASS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_CLASS]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}