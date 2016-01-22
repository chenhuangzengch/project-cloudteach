package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class TeachCoursewaresShareStatisticsDTO implements Serializable {
    private static final long serialVersionUID = -2733156457504445547L;
    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 收藏次数
     */
    private Integer collectTimes;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 浏览次数
     */
    private Integer vewingTimes;

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_STATISTICS] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_STATISTICS]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_STATISTICS] 的属性 收藏次数
     */
    public Integer getCollectTimes() {
        return collectTimes;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_STATISTICS]的属性收藏次数
     */
    public void setCollectTimes(Integer collectTimes) {
        this.collectTimes = collectTimes;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_STATISTICS] 的属性 点赞次数
     */
    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_STATISTICS]的属性点赞次数
     */
    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_STATISTICS] 的属性 浏览次数
     */
    public Integer getVewingTimes() {
        return vewingTimes;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_STATISTICS]的属性浏览次数
     */
    public void setVewingTimes(Integer vewingTimes) {
        this.vewingTimes = vewingTimes;
    }

    @Override
    public String toString() {
        return "TeachCoursewaresShareStatisticsDTO{" +
                "shareId='" + shareId + '\'' +
                ", collectTimes=" + collectTimes +
                ", praiseTimes=" + praiseTimes +
                ", vewingTimes=" + vewingTimes +
                '}';
    }
}