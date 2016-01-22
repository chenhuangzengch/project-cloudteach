package net.xuele.cloudteach.web.wrapper;

import java.util.Date;
import java.util.List;

public class BlackboardPublishWrapper {
    /**
     * 板书ID
     */
    private String blackboardId;

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


    private List<BlackboardPublishFilesWrapper> resources;


    /**
     * 获取 [CT_BLACKBOARD_PUBLISH] 的属性 板书ID
     */
    public String getBlackboardId() {
        return blackboardId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH]的属性板书ID
     */
    public void setBlackboardId(String blackboardId) {
        this.blackboardId = blackboardId == null ? null : blackboardId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH] 的属性 发布用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH]的属性发布用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public List<BlackboardPublishFilesWrapper> getResources() {
        return resources;
    }

    public void setResources(List<BlackboardPublishFilesWrapper> resources) {
        this.resources = resources;
    }
}