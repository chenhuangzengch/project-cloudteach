package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * GuidanceWorkItemAnswerDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkItemAnswerDTO implements Serializable {
    private static final long serialVersionUID = 2948333444616776337L;

    /**
     * 预习作业学生回答ID
     */
    private String answerId;

    /**
     * 预习作业ID
     */
    private String workId;

    /**
     * 预习作业预习项ID
     */
    private String workItemId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 学生班级ID
     */
    private String classId;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 老师评分
     */
    private Integer score;

    /**
     * 学生提交状态
     */
    private Integer subStatus;

    /**
     * 批改状态
     */
    private Integer correctStatus;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 提交时间
     */
    private Date subTime;

    /**
     * 最后修改人
     */
    private String updateUserId;

    /**
     * 批改时间
     */
    private Date correctTime;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 预习作业学生回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性预习作业学生回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 预习作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性预习作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 预习作业预习项ID
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性预习作业预习项ID
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 学生用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性学生用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 学生班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性学生班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 回答描述
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性回答描述
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 老师评分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性老师评分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 提交时间
     */
    public Date getSubTime() {
        return subTime;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性提交时间
     */
    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 最后修改人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性最后修改人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 批改时间
     */
    public Date getCorrectTime() {
        return correctTime;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性批改时间
     */
    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemAnswerDTO{" +
                "answerId='" + answerId + '\'' +
                ", workId='" + workId + '\'' +
                ", workItemId='" + workItemId + '\'' +
                ", userId='" + userId + '\'' +
                ", classId='" + classId + '\'' +
                ", context='" + context + '\'' +
                ", score=" + score +
                ", subStatus=" + subStatus +
                ", correctStatus=" + correctStatus +
                ", updateTime=" + updateTime +
                ", subTime=" + subTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", correctTime=" + correctTime +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}