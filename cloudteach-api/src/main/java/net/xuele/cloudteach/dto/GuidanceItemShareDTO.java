package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * GuidanceItemShareDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceItemShareDTO implements Serializable {
    private static final long serialVersionUID = 4027521291552708787L;

    /**
     * 分享预习项ID
     */
    private String shareId;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 预习项ID
     */
    private String itemId;

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
     * 区域名称
     */
    private String areaName;

    /**
     * 分享范围类型
     */
    private Integer shareType;

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
     * 分享时间
     */
    private Date shareTime;

    /**
     * 审核意见
     */
    private String opinion;

    /**
     * 状态
     */
    private Integer status;

    /*************************关联字段***************************/
    /**
     * 收藏次数
     */
    private Integer collectTimes;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 头像
     */
    private String icon;

    /**
     * 收藏状态
     */
    private int collectState;

    /**
     * 点赞状态
     */
    private int praiseState;

    /**
     * 是否我分享的
     */
    private int isMyShare;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getCollectState() {
        return collectState;
    }

    public void setCollectState(int collectState) {
        this.collectState = collectState;
    }

    public int getPraiseState() {
        return praiseState;
    }

    public void setPraiseState(int praiseState) {
        this.praiseState = praiseState;
    }

    public int getIsMyShare() {
        return isMyShare;
    }

    public void setIsMyShare(int isMyShare) {
        this.isMyShare = isMyShare;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public Integer getCollectTimes() {
        return collectTimes;
    }

    public void setCollectTimes(Integer collectTimes) {
        this.collectTimes = collectTimes;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 分享预习项ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性分享预习项ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 预习项ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性预习项ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 学校ID
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性学校ID
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 区域ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性区域ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 分享范围类型
     */
    public Integer getShareType() {
        return shareType;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性分享范围类型
     */
    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 提交方式：图片
     */
    public Integer getSubImage() {
        return subImage;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性提交方式：图片
     */
    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 提交方式：录音
     */
    public Integer getSubTape() {
        return subTape;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性提交方式：录音
     */
    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 提交方式：视频
     */
    public Integer getSubVideo() {
        return subVideo;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性提交方式：视频
     */
    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 提交方式：其他
     */
    public Integer getSubOther() {
        return subOther;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性提交方式：其他
     */
    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 置顶标志
     */
    public Integer getStickStatus() {
        return stickStatus;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性置顶标志
     */
    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 分享时间
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性分享时间
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 审核意见
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性审核意见
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_SHARE] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_SHARE]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GuidanceItemShareDTO{" +
                "shareId='" + shareId + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", shareType=" + shareType +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", context='" + context + '\'' +
                ", stickStatus=" + stickStatus +
                ", shareTime=" + shareTime +
                ", opinion='" + opinion + '\'' +
                ", status=" + status +
                ", collectTimes=" + collectTimes +
                ", praiseTimes=" + praiseTimes +
                ", icon='" + icon + '\'' +
                ", collectState=" + collectState +
                ", praiseState=" + praiseState +
                ", isMyShare=" + isMyShare +
                '}';
    }
}