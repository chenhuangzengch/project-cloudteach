package net.xuele.cloudteach.view;

import java.util.Date;

/**
 * Created by dj on 2015/7/31 0031.
 */
public class ParentCloudWorkManagerView {
    /**
     * 作业汇总表信息(ct_work_gather)
     * */
    //作业id
    private String workId;
    //教师用户id
    private String userId;
    //发布时间
    private Date publishTime;
    //最晚提交时间
    private Date endTime;
    //科目Id
    private String subjectId;
    //科目名称
    private String subjectName;
    //课程名称
    private String unitName;
    //作业描述
    private String context;
    //附件
    private String files;
    //作业类型
    private Integer workType;
    //作业题数
    private Integer workItemNum;
    //班级数
    private Integer workClassNum;
    //班级信息
    private String workClassJson;
    //作业对应学生数
    private Integer workStudentNum;
    //学校id
    private String schoolId;
    //状态
    private String status;
    /**
     * 作业学生汇总表信息(ct_work_student_gather)
     * */
    //作业学生id
    private String workStudentId;
    //班级id
    private String classId;
    //学生id
    private String studentId;
    //提交状态
    private Integer subStatus;
    //批改状态
    private Integer correctStatus;


    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getWorkItemNum() {
        return workItemNum;
    }

    public void setWorkItemNum(Integer workItemNum) {
        this.workItemNum = workItemNum;
    }

    public Integer getWorkClassNum() {
        return workClassNum;
    }

    public void setWorkClassNum(Integer workClassNum) {
        this.workClassNum = workClassNum;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getWorkStudentId() {
        return workStudentId;
    }

    public void setWorkStudentId(String workStudentId) {
        this.workStudentId = workStudentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
