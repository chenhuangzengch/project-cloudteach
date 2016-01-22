package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtTeachCoursewaresShare {
    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 用户ID:（分享人）userid
     */
    private String creator;

    /**
     * 创建用户名字
     */
    private String creatorName;
    /**
     * 原课件ID：对应我的授课课件ID
     */
    private String coursewaresId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 区域ID
     */
    private String areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 分享范围类型 1学校，2县域，3市域，4省域，0全国
     */
    private Integer shareType;

    /**
     * 课件名称
     */
    private String title;

    /**
     * 置顶标志:0否 1是
     */
    private Integer stickStatus;

    /**
     * 分享时间
     */
    private Date shareTime;

    /**
     * 审核意见
     */
    private String opinion;

    /**
     * 状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    private Integer status;

    private String audit_user_id;

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 用户ID:（分享人）userid
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性用户ID:（分享人）userid
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 原课件ID：对应我的授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性原课件ID：对应我的授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 学校ID
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性学校ID
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 区域ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性区域ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 分享范围类型 1学校，2县域，3市域，4省域，0全国
     */
    public Integer getShareType() {
        return shareType;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性分享范围类型 1学校，2县域，3市域，4省域，0全国
     */
    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 课件名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性课件名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 置顶标志:0否 1是
     */
    public Integer getStickStatus() {
        return stickStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性置顶标志:0否 1是
     */
    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 分享时间
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性分享时间
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 审核意见
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性审核意见
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAudit_user_id() {
        return audit_user_id;
    }

    public void setAudit_user_id(String audit_user_id) {
        this.audit_user_id = audit_user_id;
    }
}