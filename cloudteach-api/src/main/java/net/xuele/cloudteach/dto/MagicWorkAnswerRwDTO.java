package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class MagicWorkAnswerRwDTO implements Serializable {
    private static final long serialVersionUID = 6893815356885459674L;
    /**
     * 回答对错记录ID
     */
    private String rwId;

    /**
     * 作业ID
     */
    private String workId;

    /**
     * 提分宝作业回答ID
     */
    private String answerId;

    /**
     * 题库ID
     */
    private String bankId;

    /**
     * 题目ID
     */
    private String queId;

    /**
     * 回答正确状态:0错误 1正确
     */
    private Integer rwStatus;

    /**
     * 状态：0删除 1有效
     */
    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 回答对错记录ID
     */
    public String getRwId() {
        return rwId;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性回答对错记录ID
     */
    public void setRwId(String rwId) {
        this.rwId = rwId == null ? null : rwId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 提分宝作业回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性提分宝作业回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 题库ID
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性题库ID
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 题目ID
     */
    public String getQueId() {
        return queId;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性题目ID
     */
    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 回答正确状态:0错误 1正确
     */
    public Integer getRwStatus() {
        return rwStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性回答正确状态:0错误 1正确
     */
    public void setRwStatus(Integer rwStatus) {
        this.rwStatus = rwStatus;
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_RW] 的属性 状态：0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_RW]的属性状态：0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkAnswerRwDTO{" +
                "rwId='" + rwId + '\'' +
                ", workId='" + workId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", queId='" + queId + '\'' +
                ", rwStatus=" + rwStatus +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}