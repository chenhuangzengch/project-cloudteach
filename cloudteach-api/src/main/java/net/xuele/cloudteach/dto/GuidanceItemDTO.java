package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * GuidanceItemDTO
 *
 * @author sunxh
 * @date 2015/7/8 0002
 */
public class GuidanceItemDTO implements Serializable {
    private static final long serialVersionUID = 6085323309580416837L;

    /**
     * 预习项ID
     */
    private String itemId;

    /**
     * 创建者userid
     */
    private String creator;

    /**
     * 对应的课程ID
     */
    private String unitId;

    /**
     * 是否为收藏
     */
    private Integer isCollect;

    /**
     * 所属用户ID
     */
    private String userId;

    /**
     * 收藏预习项来源id
     */
    private String pid;

    /**
     * 提交方式：图片
     */
    private Integer subImage;

    /**
     * 提交方式：录音
     */
    private Integer subTape;

    /**
     * 提交方式：视频
     */
    private Integer subVideo;

    /**
     * 提交方式：其他
     */
    private Integer subOther;

    /**
     * 描述内容
     */
    private String context;

    /**
     * 置顶标志
     */
    private Integer stickStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 分享状态
     */
    private Integer shareStatus;

    /**
     * 学校id--用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发布次数
     */
    private Integer releases;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 预习项ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性预习项ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 创建者userid
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性创建者userid
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 对应的课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性对应的课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 是否为收藏
     */
    public Integer getIsCollect() {
        return isCollect;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性是否为收藏
     */
    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 所属用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性所属用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 收藏预习项来源id
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性收藏预习项来源id
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 提交方式：图片
     */
    public Integer getSubImage() {
        return subImage;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性提交方式：图片
     */
    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 提交方式：录音
     */
    public Integer getSubTape() {
        return subTape;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性提交方式：录音
     */
    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 提交方式：视频
     */
    public Integer getSubVideo() {
        return subVideo;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性提交方式：视频
     */
    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 提交方式：其他
     */
    public Integer getSubOther() {
        return subOther;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性提交方式：其他
     */
    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 置顶标志
     */
    public Integer getStickStatus() {
        return stickStatus;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性置顶标志
     */
    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 最后修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性最后修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 分享状态
     */
    public Integer getShareStatus() {
        return shareStatus;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性分享状态
     */
    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReleases() {
        return releases;
    }

    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    @Override
    public String toString() {
        return "GuidanceItemDTO{" +
                "itemId='" + itemId + '\'' +
                ", creator='" + creator + '\'' +
                ", unitId='" + unitId + '\'' +
                ", isCollect=" + isCollect +
                ", userId='" + userId + '\'' +
                ", pid='" + pid + '\'' +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", context='" + context + '\'' +
                ", stickStatus=" + stickStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", shareStatus=" + shareStatus +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                ", releases=" + releases +
                '}';
    }
}