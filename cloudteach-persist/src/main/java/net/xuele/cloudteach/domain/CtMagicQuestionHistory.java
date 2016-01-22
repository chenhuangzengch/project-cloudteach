package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtMagicQuestionHistory {
    /**
     * 唯一主键
     */
    private String hisId;

    /**
     * 操作用户
     */
    private String opUser;

    /**
     * 操作详情
     */
    private String opDetail;

    /**
     * 记录创建者
     */
    private String createUser;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 状态（0：存在，1：不存在）
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 唯一主键
     */
    public String getHisId() {
        return hisId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性唯一主键
     */
    public void setHisId(String hisId) {
        this.hisId = hisId == null ? null : hisId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 操作用户
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性操作用户
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser == null ? null : opUser.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 操作详情
     */
    public String getOpDetail() {
        return opDetail;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性操作详情
     */
    public void setOpDetail(String opDetail) {
        this.opDetail = opDetail == null ? null : opDetail.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 记录创建者
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性记录创建者
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_HISTORY] 的属性 状态（0：存在，1：不存在）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_HISTORY]的属性状态（0：存在，1：不存在）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}