package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtWorkClassGather {
    /**
     * 作业学生ID(UUID)
     */
    private String workClassId;

    /**
     * 作业ID
     */
    private String workId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 上次提醒交作业时间
     */
    private Date lastWarnTime;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;


    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId;
    }

    /**
     * 获取 [CT_WORK_CLASS_GATHER] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_WORK_CLASS_GATHER]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_WORK_CLASS_GATHER] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_WORK_CLASS_GATHER]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_WORK_CLASS_GATHER] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_WORK_CLASS_GATHER]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_WORK_CLASS_GATHER] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_WORK_CLASS_GATHER]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastWarnTime() {
        return lastWarnTime;
    }

    public void setLastWarnTime(Date lastWarnTime) {
        this.lastWarnTime = lastWarnTime;
    }
}