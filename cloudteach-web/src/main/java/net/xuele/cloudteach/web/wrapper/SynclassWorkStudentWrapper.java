package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * SynclassWorkStudentWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkStudentWrapper {
    /**
     * uuid
     */
    private String workUserId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 同步课堂作业ID
     */
    private String workId;
    /**
     * 学生班级ID
     */
    private String classId;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 提交状态
     */
    private Integer subStatus;

    /**
     * 批改状态
     */
    private Integer correctStatus;

    /**
     * 批改时间
     */
    private Date correctTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 uuid
     */
    public String getWorkUserId() {
        return workUserId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性uuid
     */
    public void setWorkUserId(String workUserId) {
        this.workUserId = workUserId == null ? null : workUserId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 学生用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性学生用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 同步课堂作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性同步课堂作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 回答描述
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性回答描述
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 提交状态
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性提交状态
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 批改时间
     */
    public Date getCorrectTime() {
        return correctTime;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性批改时间
     */
    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}