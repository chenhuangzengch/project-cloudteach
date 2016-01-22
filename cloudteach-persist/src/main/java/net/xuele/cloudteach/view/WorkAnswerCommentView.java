package net.xuele.cloudteach.view;

import java.util.Date;

public class WorkAnswerCommentView {
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 作业学生回答ID
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
    /**
     * 点评/评论用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 教师职务
     */
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}