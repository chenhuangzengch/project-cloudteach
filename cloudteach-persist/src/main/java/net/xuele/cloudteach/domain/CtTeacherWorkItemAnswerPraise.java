package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtTeacherWorkItemAnswerPraise {

    /**
     * 点赞ID
     */
    private String praiseId;

    /**
     * 回答ID（对应教师作业题目学生回答表主键）
     */
    private String answerId;

    /**
     * 点赞用户ID
     */
    private String userId;

    /**
     * 用户类型（1老师，2学生）
     */
    private Integer userType;

    /**
     * 点赞时间
     */
    private Date praiseTime;

    /**
     * 用户点赞状态（0，未点赞，1点赞）
     */
    private Integer praiseStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 回答ID（对应教师作业题目学生回答表主键）
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性回答ID（对应教师作业题目学生回答表主键）
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 点赞用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性点赞用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 用户类型（1老师，2学生）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性用户类型（1老师，2学生）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 点赞时间
     */
    public Date getPraiseTime() {
        return praiseTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性点赞时间
     */
    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 用户点赞状态（0，未点赞，1点赞）
     */
    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性用户点赞状态（0，未点赞，1点赞）
     */
    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_PRAISE] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_PRAISE]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(String praiseId) {
        this.praiseId = praiseId;
    }
}