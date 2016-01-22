package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

public class TeachCoursewaresSharePraiseWrapper {
    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 点赞用户ID
     */
    private String userId;

    /**
     * 点赞时间
     */
    private Date praiseTime;

    /**
     * 用户点赞状态:0取消点赞，1已点赞
     */
    private Integer praiseStatus;

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_PRAISE] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_PRAISE]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_PRAISE] 的属性 点赞用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_PRAISE]的属性点赞用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_PRAISE] 的属性 点赞时间
     */
    public Date getPraiseTime() {
        return praiseTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_PRAISE]的属性点赞时间
     */
    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }
}