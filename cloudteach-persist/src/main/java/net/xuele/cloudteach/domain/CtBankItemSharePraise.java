package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtBankItemSharePraise {
    /**
     * 主键
     */
    private String sharePraiseId;
    /**
     * 分享题目ID（分享教师题目表主键）
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
     * 用户点赞状态
     */
    private Integer praiseStatus;

    public String getSharePraiseId() {
        return sharePraiseId;
    }

    public void setSharePraiseId(String sharePraiseId) {
        this.sharePraiseId = sharePraiseId;
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_PRAISE] 的属性 分享题目ID（分享教师题目表主键）
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_PRAISE]的属性分享题目ID（分享教师题目表主键）
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_PRAISE] 的属性 点赞用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_PRAISE]的属性点赞用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_PRAISE] 的属性 点赞时间
     */
    public Date getPraiseTime() {
        return praiseTime;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_PRAISE]的属性点赞时间
     */
    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_PRAISE] 的属性 用户点赞状态
     */
    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_PRAISE]的属性用户点赞状态
     */
    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }
}