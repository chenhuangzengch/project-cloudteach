package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtWorkStudentGather {

    /**
     * 作业学生id
     */
    private String workStudentId;
    /**
     * 作业ID
     */
    private String workId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 提交状态:0未提交1已提交
     */
    private Integer subStatus;

    /**
     * 批改状态:0为批改1已批改
     */
    private Integer correctStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    private Integer viewStatus;

    private Date viewTime;

    private Date subTime;

    private Date correctTime;

    private Integer playTimes;

    public Integer getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }

    public Date getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public String getWorkStudentId() {
        return workStudentId;
    }

    public void setWorkStudentId(String workStudentId) {
        this.workStudentId = workStudentId;
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 学生ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性学生ID
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 提交状态:0未提交1已提交
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性提交状态:0未提交1已提交
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 批改状态:0为批改1已批改
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性批改状态:0为批改1已批改
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_WORK_STUDENT_GATHER] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_WORK_STUDENT_GATHER]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}