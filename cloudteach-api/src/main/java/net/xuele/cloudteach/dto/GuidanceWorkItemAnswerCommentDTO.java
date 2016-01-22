package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * GuidanceWorkItemAnswerCommentDTo
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkItemAnswerCommentDTO implements Serializable {
    private static final long serialVersionUID = 5338473094881350175L;
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

    private String userType;

    /**
     * 评论内容
     */
    private String context;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemAnswerCommentDTO{" +
                "commentId='" + commentId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", context='" + context + '\'' +
                ", commentTime=" + commentTime +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}