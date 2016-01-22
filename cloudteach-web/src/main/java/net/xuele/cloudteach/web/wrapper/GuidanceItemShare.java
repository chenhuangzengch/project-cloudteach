package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.GuidanceItemShareDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 大家分享的预习项信息
 * GuidanceItemShareWrapper
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceItemShare {
    /**
     * 分享预习项ID
     */
    private String shareId;

    /**
     * 创建者
     */
    private String creator;


    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 区域ID
     */
    private String areaId;

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
     * 状态
     */
    private Integer status;

/*******************************************************************************************/
    /**
     * 收藏次数
     */
    private Integer collectTimes;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 头像
     */
    private String icon;

    /**
     * 学校名称
     */
    private String schoolName;

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

    public Integer getCollectTimes() {
        return collectTimes;
    }

    public void setCollectTimes(Integer collectTimes) {
        this.collectTimes = collectTimes;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    /**
     * 初始化
     * @param guidanceItemShareDTO
     */
    public GuidanceItemShare(GuidanceItemShareDTO guidanceItemShareDTO) {
        BeanUtils.copyProperties(guidanceItemShareDTO, this);
    }
}