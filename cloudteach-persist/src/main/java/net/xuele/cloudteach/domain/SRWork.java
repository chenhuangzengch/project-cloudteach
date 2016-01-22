package net.xuele.cloudteach.domain;

import java.util.Date;

/**
 * Created by cm.wang on 2015/8/13 0013.
 */
public class SRWork {

    private String userId;

    private String workId;

    private String schoolId;

    /**
     * 发布时间
     */
    private Date publishTime;

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
     * 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    private Integer workType;

    /**
     * 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    private Integer workItemNum;

    /**
     * 班级信息(jason格式)包括班级编号，班级名称
     */
    private String workClassJson;

    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

    /**
     * 学生提交数
     */
    private Integer subStudentNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getWorkItemNum() {
        return workItemNum;
    }

    public void setWorkItemNum(Integer workItemNum) {
        this.workItemNum = workItemNum;
    }

    public String getWorkClassJson() {
        return workClassJson;
    }

    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson;
    }

    public Integer getWorkStudentNum() {
        return workStudentNum;
    }

    public void setWorkStudentNum(Integer workStudentNum) {
        this.workStudentNum = workStudentNum;
    }

    public Integer getSubStudentNum() {
        return subStudentNum;
    }

    public void setSubStudentNum(Integer subStudentNum) {
        this.subStudentNum = subStudentNum;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
