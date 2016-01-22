package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class WorkGatherDTO implements Serializable {

    private static final long serialVersionUID = -6982830157412288850L;
    /**
     * 作业ID
     */
    private String workId;

    /**
     * 版本号：用于乐观锁
     */
    private Integer version;

    /**
     * 教师用户ID
     */
    private String userId;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 科目ID
     */
    private String subjectId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 课程名称
     */
    private String unitName;

    /**
     * 作业描述
     */
    private String context;

    /**
     * 附件数组：{id:'id',uri:'uri',name:'name',ext:'ext'} json数组
     */
    private String files;

    /**
     * 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    private Integer workType;

    /**
     * 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    private Integer workItemNum;

    /**
     * 班级数
     */
    private Integer workClassNum;

    /**
     * 班级信息(jason格式)包括班级编号，班级名称
     */
    private String workClassJson;

    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态:0删除 1有效
     */
    private Integer status;

    /**
     * 获取 [CT_WORK_GATHER] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 版本号：用于乐观锁
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性版本号：用于乐观锁
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 教师用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性教师用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 课程名称
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性课程名称
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 作业描述
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性作业描述
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 附件数组：{id:'id',uri:'uri',ext:'ext'} json数组
     */
    public String getFiles() {
        return files;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性附件数组：{id:'id',uri:'uri',ext:'ext'} json数组
     */
    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    public Integer getWorkType() {
        return workType;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    public Integer getWorkItemNum() {
        return workItemNum;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    public void setWorkItemNum(Integer workItemNum) {
        this.workItemNum = workItemNum;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 班级数
     */
    public Integer getWorkClassNum() {
        return workClassNum;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性班级数
     */
    public void setWorkClassNum(Integer workClassNum) {
        this.workClassNum = workClassNum;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 班级信息(jason格式)包括班级编号，班级名称
     */
    public String getWorkClassJson() {
        return workClassJson;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性班级信息(jason格式)包括班级编号，班级名称
     */
    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson == null ? null : workClassJson.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 作业对应学生数
     */
    public Integer getWorkStudentNum() {
        return workStudentNum;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性作业对应学生数
     */
    public void setWorkStudentNum(Integer workStudentNum) {
        this.workStudentNum = workStudentNum;
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER] 的属性 状态:0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_WORK_GATHER]的属性状态:0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkGatherDTO{" +
                "workId='" + workId + '\'' +
                ", version=" + version +
                ", userId='" + userId + '\'' +
                ", publishTime=" + publishTime +
                ", subjectId='" + subjectId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", context='" + context + '\'' +
                ", files='" + files + '\'' +
                ", workType=" + workType +
                ", workItemNum=" + workItemNum +
                ", workClassNum=" + workClassNum +
                ", workClassJson='" + workClassJson + '\'' +
                ", workStudentNum=" + workStudentNum +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}