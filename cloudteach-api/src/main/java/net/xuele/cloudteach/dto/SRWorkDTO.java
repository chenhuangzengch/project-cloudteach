package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cm.wang on 2015/8/11 0011.
 */
public class SRWorkDTO implements Serializable{

    private static final long serialVersionUID = 980505329690597727L;

    private String workId;

    private String schoolId;

    /**
     * 发布时间
     */
    private String publishTime;

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

    // 教师名字
    private String teacherName;

    // 教师头像
    private String teacherHeadIcon;

    // 教师职称
    private String positionName;

    //口语作业
    private WorkTapeFilesDTO workTapeFilesDTO;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherHeadIcon() {
        return teacherHeadIcon;
    }

    public void setTeacherHeadIcon(String teacherHeadIcon) {
        this.teacherHeadIcon = teacherHeadIcon;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }
}
