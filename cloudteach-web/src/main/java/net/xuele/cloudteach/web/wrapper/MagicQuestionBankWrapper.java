package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

public class MagicQuestionBankWrapper{
    /**
     * 题库id
     */
    private String bankId;

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
    private String createuser;

    /**
     * 记录创建时间
     */
    private Date createtime;

    /**
     * 状态（1，有效，0，无效）
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 题库id
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性题库id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 题库名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性题库名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 单元
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性单元
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 课程编号(关联课程表id）
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性课程编号(关联课程表id）
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 课题
     */
    public String getTopic() {
        return topic;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性课题
     */
    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 课时
     */
    public String getClasses() {
        return classes;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性课时
     */
    public void setClasses(String classes) {
        this.classes = classes == null ? null : classes.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 记录创建者
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性记录创建者
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 记录创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性记录创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK] 的属性 状态（1，有效，0，无效）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK]的属性状态（1，有效，0，无效）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}