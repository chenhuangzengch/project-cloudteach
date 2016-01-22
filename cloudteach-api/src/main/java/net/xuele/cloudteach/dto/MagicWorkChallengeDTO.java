package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class MagicWorkChallengeDTO implements Serializable {

    private static final long serialVersionUID = 5283680278989140759L;
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
     * 作业开始时间
     */
    private Date beginTime;

    /**
     * 作业完成时间
     */
    private Date endTime;

    /**
     * 题目总数
     */
    private Integer totalQuenum;

    /**
     * 答对题目数
     */
    private Integer rightQuenum;

    /**
     * 作业分数
     */
    private Integer score;

    /**
     * 挑战成绩描述
     */
    private String scorecontext;

    /**
     * 分享状态(0不分享1分享到作业交流2提交给老师)
     */
    private Integer shareStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态：0删除 1有效
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 挑战记录ID（对应挑战记录表主键）
     */
    public String getChallengeId() {
        return challengeId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性挑战记录ID（对应挑战记录表主键）
     */
    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId == null ? null : challengeId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 作业ID（如果是应用中心入口进行挑战为空）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性作业ID（如果是应用中心入口进行挑战为空）
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
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 题库ID
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性题库ID
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 第几套题目ID
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性第几套题目ID
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 作业开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性作业开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 作业完成时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性作业完成时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 题目总数
     */
    public Integer getTotalQuenum() {
        return totalQuenum;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性题目总数
     */
    public void setTotalQuenum(Integer totalQuenum) {
        this.totalQuenum = totalQuenum;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 答对题目数
     */
    public Integer getRightQuenum() {
        return rightQuenum;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性答对题目数
     */
    public void setRightQuenum(Integer rightQuenum) {
        this.rightQuenum = rightQuenum;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 作业分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性作业分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 挑战成绩描述
     */
    public String getScorecontext() {
        return scorecontext;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性挑战成绩描述
     */
    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext == null ? null : scorecontext.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 分享状态(0不分享1分享到作业交流2提交给老师)
     */
    public Integer getShareStatus() {
        return shareStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性分享状态(0不分享1分享到作业交流2提交给老师)
     */
    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_CHALLENGE] 的属性 状态：0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_WORK_CHALLENGE]的属性状态：0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkChallengeDTO{" +
                "challengeId='" + challengeId + '\'' +
                ", workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", orderNum=" + orderNum +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", totalQuenum=" + totalQuenum +
                ", rightQuenum=" + rightQuenum +
                ", score=" + score +
                ", scorecontext='" + scorecontext + '\'' +
                ", shareStatus=" + shareStatus +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}