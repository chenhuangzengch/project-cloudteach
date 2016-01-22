package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtTeacherWorkItemAnswerComment {
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回答ID（对应教师作业题目学生回答表主键）
     */
    private String answerId;

    /**
     * 点评/评论用户ID
     */
    private String userId;

    /**
     * 用户类型（1老师,2学生）
     */
    private Integer userType;

    /**
     * 评论内容
     */
    private String context;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 评论id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性评论id
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 回答ID（对应教师作业题目学生回答表主键）
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性回答ID（对应教师作业题目学生回答表主键）
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 点评/评论用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性点评/评论用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 用户类型（1老师,2学生）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性用户类型（1老师,2学生）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 评论内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性评论内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 评论时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性评论时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_COMMENT] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_COMMENT]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}