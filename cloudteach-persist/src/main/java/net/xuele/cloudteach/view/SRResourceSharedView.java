package net.xuele.cloudteach.view;

import java.util.Date;

/**
 * SRResourceSharedView
 * 区划下的最新分享信息
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRResourceSharedView {

    /**
     * 课程名称
     */
    private String unitName;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 年级
     */
    private int grade;

    /**
     * 分享人名称
     */
    private String userName;

    /**
     * 分享人学校
     */
    private String schoolName;

    /**
     * 分享人职务
     */
    private String userDutyName;

    /**
     * 分享时间
     */
    private Date shareTime;

    /**
     * 文件类型 1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 文件图片url fileKey
     */
    private String fileUri;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件后缀
     */
    private String extention;

    /**
     * 文件后缀类型
     */
    private Integer extType;

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUserDutyName() {
        return userDutyName;
    }

    public void setUserDutyName(String userDutyName) {
        this.userDutyName = userDutyName;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }
}
