package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtTeacherWorkItemAnswer {
    /**
     * 教师作业题目学生回答ID
     */
    private String answerId;

    /**
     * 作业ID（对应教师作业表主键）
     */
    private String workId;

    /**
     * 题目ID（对应教师作业题目表主键）
     */
    private String workItemId;

    /**
     * 学生ID
     */
    private String userId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 老师评分 （电子作业与预习作业1:A,2:B,3:C,4:D;）
     */
    private Integer score;

    /**
     * 系统评分 （口语作业系统评分）
     */
    private Integer sysScore;

    /**
     * 提交状态：0未提交，1已提交
     */
    private Integer subStatus;

    /**
     * 提交时间：0未批改，1已批改
     */
    private Date subTime;

    /**
     * 批改状态
     */
    private Integer correctStatus;

    /**
     * 批改时间
     */
    private Date correctTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 最后修改人
     */
    private String updateUserId;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 教师作业题目学生回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性教师作业题目学生回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 作业ID（对应教师作业表主键）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性作业ID（对应教师作业表主键）
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 题目ID（对应教师作业题目表主键）
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性题目ID（对应教师作业题目表主键）
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 学生ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性学生ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 回答描述
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性回答描述
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 老师评分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性老师评分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 系统评分
     */
    public Integer getSysScore() {
        return sysScore;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性系统评分
     */
    public void setSysScore(Integer sysScore) {
        this.sysScore = sysScore;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 提交状态：0未提交，1已提交
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性提交状态：0未提交，1已提交
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 提交时间：0未批改，1已批改
     */
    public Date getSubTime() {
        return subTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性提交时间：0未批改，1已批改
     */
    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 批改时间
     */
    public Date getCorrectTime() {
        return correctTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性批改时间
     */
    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 最后修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性最后修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}