package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class TeachCoursewaresShareCollectDTO implements Serializable {
    private static final long serialVersionUID = -2506674477948409448L;
    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 收藏用户ID
     */
    private String userId;

    /**
     * 收藏时间
     */
    private Date collectTime;

    /**
     * 用户收藏状态：0取消收藏，1已收藏
     */
    private Integer collectStatus;

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 收藏用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性收藏用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 收藏时间
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性收藏时间
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 用户收藏状态：0取消收藏，1已收藏
     */
    public Integer getCollectStatus() {
        return collectStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性用户收藏状态：0取消收藏，1已收藏
     */
    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    @Override
    public String toString() {
        return "TeachCoursewaresShareCollectDTO{" +
                "shareId='" + shareId + '\'' +
                ", userId='" + userId + '\'' +
                ", collectTime=" + collectTime +
                ", collectStatus=" + collectStatus +
                '}';
    }
}