package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * MagicQuestionDTO
 * 提分宝题库题目
 * @author duzg
 * @date 2015/7/13 0002
 */
public class MagicQuestionDTO implements Serializable {
    private static final long serialVersionUID = 5579474901373412658L;
    /**
     * 题目id
     */
    private String queId;

    /**
     * 题库id
     */
    private String bankId;

    /**
     * 分类号(1选择题 2填空题 3判断题...)
     */
    private Integer queTypeCode;

    /**
     * 题目类型
     */
    private String queType;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;

    /**
     * 难度（随难度增加变大）（保留字段）
     */
    private Integer difficulty;

    /**
     * 原题id
     */
    private String originalQuestionId;

    /**
     * 衍生题顺序（从1开始，原题=0）（套数）
     */
    private Integer orderNum;

    /**
     * 记录创建者名称
     */
    private String createUser;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 状态（1，有效。0，无效）
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 题目id
     */
    public String getQueId() {
        return queId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性题目id
     */
    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 题库id
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性题库id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 分类号(1选择题 2填空题 3判断题...)
     */
    public Integer getQueTypeCode() {
        return queTypeCode;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性分类号(1选择题 2填空题 3判断题...)
     */
    public void setQueTypeCode(Integer queTypeCode) {
        this.queTypeCode = queTypeCode;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 题目类型
     */
    public String getQueType() {
        return queType;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性题目类型
     */
    public void setQueType(String queType) {
        this.queType = queType == null ? null : queType.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 题目内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性题目内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性答案
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 难度（随难度增加变大）（保留字段）
     */
    public Integer getDifficulty() {
        return difficulty;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性难度（随难度增加变大）（保留字段）
     */
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getOriginalQuestionId() {
        return originalQuestionId;
    }

    public void setOriginalQuestionId(String originalQuestionId) {
        this.originalQuestionId = originalQuestionId;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 衍生题顺序（从1开始，原题=0）（套数）
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性衍生题顺序（从1开始，原题=0）（套数）
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 记录创建者名称
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性记录创建者名称
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION] 的属性 状态（1，有效。0，无效）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_QUESTION]的属性状态（1，有效。0，无效）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicQuestionDTO{" +
                "queId='" + queId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", queTypeCode=" + queTypeCode +
                ", queType='" + queType + '\'' +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", difficulty=" + difficulty +
                ", originalQuestionId='" + originalQuestionId + '\'' +
                ", orderNum=" + orderNum +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}