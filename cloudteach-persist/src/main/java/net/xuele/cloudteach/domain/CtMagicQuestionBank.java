package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtMagicQuestionBank {
    /**
     * 题库id
     */
    private String bankId;

    /**
     * 教辅id
     */
    private String extraBookId;

    /**
     * 题库名称
     */
    private String title;

    /**
     * 单元
     */
    private String unit;

    /**
     * 课程编号(关联课程表id）
     */
    private String unitId;

    /**
     * 课题
     */
    private String topic;

    /**
     * 课时
     */
    private String classes;

    /**
     * 记录创建者
     */
    private String createUser;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 状态（1，有效，0，无效）
     */
    private Integer status;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getExtraBookId() {
        return extraBookId;
    }

    public void setExtraBookId(String extraBookId) {
        this.extraBookId = extraBookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}