package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkAnswerCommentViewDTO
 * 作业回答评论VIEW
 *
 * @author duzg
 * @date 2015/7/17 0002
 */
public class WorkAnswerCommentViewDTO implements Serializable {
    private static final long serialVersionUID = -4597304969746065482L;
    /**
     * 点评/评论用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 预习作业学生回答ID
     */
    private String answerId;

    /**
     * 点评/评论用户ID
     */
    private String userId;

    /**
     * 评论内容
     */
    private String context;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 状态
     */
    private Integer status;

    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 评论id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性评论id
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 预习作业学生回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性预习作业学生回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 点评/评论用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性点评/评论用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 评论内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性评论内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 评论时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性评论时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_COMMENT]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    private String icon;

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

    @Override
    public String toString() {
        return "WorkAnswerCommentViewDTO{" +
                "userName='" + userName + '\'' +
                ", commentId='" + commentId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", userId='" + userId + '\'' +
                ", context='" + context + '\'' +
                ", commentTime=" + commentTime +
                ", status=" + status +
                ", positionName='" + positionName + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}