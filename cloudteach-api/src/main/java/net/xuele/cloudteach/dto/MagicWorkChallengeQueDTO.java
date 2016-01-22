package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class MagicWorkChallengeQueDTO implements Serializable {

    private static final long serialVersionUID = -3219942388452589917L;
    /**
     * 挑战题目ID（UUID）
     */
    private String cqId;

    /**
     * 挑战记录ID（对应挑战记录表主键）
     */
    private String challengeId;

    /**
     * 作业ID（如果是应用中心入口进行挑战为空）
     */
    private String workId;

    /**
     * 学生ID
     */
    private String userId;

    /**
     * 题库ID
     */
    private String bankId;

    /**
     * 第几套题目ID
     */
    private Integer orderNum;

    /**
     * 题目ID
     */
    private String queId;

    /**
     * 原题ID
     */
    private String originalQuestionid;

    /**
     * 回答正确状态:0错误 1正确
     */
    private Integer rwStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态：0删除 1有效
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 挑战题目ID（UUID）
     */
    public String getCqId() {
        return cqId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性挑战题目ID（UUID）
     */
    public void setCqId(String cqId) {
        this.cqId = cqId == null ? null : cqId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 挑战记录ID（对应挑战记录表主键）
     */
    public String getChallengeId() {
        return challengeId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性挑战记录ID（对应挑战记录表主键）
     */
    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId == null ? null : challengeId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 作业ID（如果是应用中心入口进行挑战为空）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性作业ID（如果是应用中心入口进行挑战为空）
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 题库ID
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性题库ID
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 第几套题目ID
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性第几套题目ID
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 题目ID
     */
    public String getQueId() {
        return queId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性题目ID
     */
    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 原题ID
     */
    public String getOriginalQuestionid() {
        return originalQuestionid;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性原题ID
     */
    public void setOriginalQuestionid(String originalQuestionid) {
        this.originalQuestionid = originalQuestionid == null ? null : originalQuestionid.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 回答正确状态:0错误 1正确
     */
    public Integer getRwStatus() {
        return rwStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性回答正确状态:0错误 1正确
     */
    public void setRwStatus(Integer rwStatus) {
        this.rwStatus = rwStatus;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE_QUE] 的属性 状态：0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE_QUE]的属性状态：0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkChallengeQueDTO{" +
                "cqId='" + cqId + '\'' +
                ", challengeId='" + challengeId + '\'' +
                ", workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", orderNum=" + orderNum +
                ", queId='" + queId + '\'' +
                ", originalQuestionid='" + originalQuestionid + '\'' +
                ", rwStatus=" + rwStatus +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}