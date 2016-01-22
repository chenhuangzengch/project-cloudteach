package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtBankItemShareCollect {
    /**
     * 主键
     */
    private String shareCollectId;
    /**
     * 分享题目ID（分享教师题目表主键）
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
     * 用户收藏状态
     */
    private Integer collectStatus;

    public String getShareCollectId() {
        return shareCollectId;
    }

    public void setShareCollectId(String shareCollectId) {
        this.shareCollectId = shareCollectId;
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_COLLECT] 的属性 分享题目ID（分享教师题目表主键）
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_COLLECT]的属性分享题目ID（分享教师题目表主键）
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_COLLECT] 的属性 收藏用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_COLLECT]的属性收藏用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_COLLECT] 的属性 收藏时间
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_COLLECT]的属性收藏时间
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取 [CT_BANK_ITEM_SHARE_COLLECT] 的属性 用户收藏状态
     */
    public Integer getCollectStatus() {
        return collectStatus;
    }

    /**
     * 设置[CT_BANK_ITEM_SHARE_COLLECT]的属性用户收藏状态
     */
    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}