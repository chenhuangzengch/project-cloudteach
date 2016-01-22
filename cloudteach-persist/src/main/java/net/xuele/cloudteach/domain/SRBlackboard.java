package net.xuele.cloudteach.domain;

import java.util.Date;

/**
 * Created by cm.wang on 2015/8/16 0016.
 */
public class SRBlackboard {

    private String workId;

    private String userId;

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
     * 班级信息(jason格式)包括班级编号，班级名称
     */
    private String workClassJson;

    private String files;

    private String schoolName;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getWorkClassJson() {
        return workClassJson;
    }

    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
