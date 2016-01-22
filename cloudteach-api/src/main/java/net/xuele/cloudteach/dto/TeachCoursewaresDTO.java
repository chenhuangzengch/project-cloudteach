package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class TeachCoursewaresDTO implements Serializable {
    private static final long serialVersionUID = 3338792711902403986L;
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 用户ID:（我的课件所属人）userid
     */
    private String creator;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 是否为收藏0否 1是
     */
    private Integer isCollect;

    /**
     * 所属用户ID
     */
    private String userId;

    /**
     * 收藏课件来源ID：对应大家分享的课件ID
     */
    private String pid;

    /**
     * 课件名称
     */
    private String title;

    /**
     * 置顶标志：0否 1是
     */
    private Integer stickStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 分享状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    private Integer shareStatus;

    /**
     * 状态:0删除  1有效
     */
    private Integer status;
    private String SchoolId;

    /**
     * 打包状态:0未打包,1打包中,2打包成功,3打包失败
     */
    @Deprecated
    private Integer packStatus;

    /**
     * 打包课件存放的文件系统路径
     */
    @Deprecated
    private String fileUri;

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 用户ID:（我的课件所属人）userid
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性用户ID:（我的课件所属人）userid
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 是否为收藏0否 1是
     */
    public Integer getIsCollect() {
        return isCollect;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性是否为收藏0否 1是
     */
    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 所属用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性所属用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 收藏课件来源ID：对应大家分享的课件ID
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性收藏课件来源ID：对应大家分享的课件ID
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 课件名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性课件名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 置顶标志：0否 1是
     */
    public Integer getStickStatus() {
        return stickStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性置顶标志：0否 1是
     */
    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 分享状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    public Integer getShareStatus() {
        return shareStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性分享状态:0取消分享，1分享审核中，2已分享，3审核失败
     */
    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES] 的属性 状态:0删除  1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES]的属性状态:0删除  1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Deprecated
    public Integer getPackStatus() {
        return packStatus;
    }

    @Deprecated
    public void setPackStatus(Integer packStatus) {
        this.packStatus = packStatus;
    }

    @Deprecated
    public String getFileUri() {
        return fileUri;
    }

    @Deprecated
    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public String toString() {
        return "TeachCoursewaresDTO{" +
                "coursewaresId='" + coursewaresId + '\'' +
                ", creator='" + creator + '\'' +
                ", unitId='" + unitId + '\'' +
                ", isCollect=" + isCollect +
                ", userId='" + userId + '\'' +
                ", pid='" + pid + '\'' +
                ", title='" + title + '\'' +
                ", stickStatus=" + stickStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", shareStatus=" + shareStatus +
                ", status=" + status +
                ", SchoolId='" + SchoolId + '\'' +
                ", packStatus=" + packStatus +
                ", fileUri='" + fileUri + '\'' +
                '}';
    }
}