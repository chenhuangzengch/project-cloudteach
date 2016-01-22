package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cm.wang on 2015/8/11 0011.
 */
public class SRBlackboardDTO implements Serializable{


    private static final long serialVersionUID = -164334110587355058L;

    private String publishTime;

    /**
     * 作业描述
     */
    private String context;

    /**
     * 课程名称
     */
    private String unitName;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 班级信息(jason格式)包括班级编号，班级名称
     */
    private String workClassJson;

    /**
     * 附件信息
     */
    private String files;

    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

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

    public String getWorkClassJson() {
        return workClassJson;
    }

    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getWorkStudentNum() {
        return workStudentNum;
    }

    public void setWorkStudentNum(Integer workStudentNum) {
        this.workStudentNum = workStudentNum;
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

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }

    @Override
    public String toString() {
        return "SRBlackboardDTO{" +
                "publishTime=" + publishTime +
                ", context='" + context + '\'' +
                ", unitName='" + unitName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", workClassJson='" + workClassJson + '\'' +
                ", files='" + files + '\'' +
                ", workStudentNum=" + workStudentNum +
                ", teacherName='" + teacherName + '\'' +
                ", teacherHeadIcon='" + teacherHeadIcon + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
