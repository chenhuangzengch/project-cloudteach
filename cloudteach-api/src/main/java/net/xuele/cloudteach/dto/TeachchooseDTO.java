package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * TeachchooseDTO
 *
 * @author hushengguo
 * @date 2015/11/4
 */
public class TeachchooseDTO implements Serializable {

    private static final long serialVersionUID = 6964293546264074503L;

    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 授课课件title
     */
    private String coursewaresTitle;

    /**
     * 授课课件学校
     */
    private String schoolId;

    /**
     * 授课课件创建人
     */
    private String userId;

    /**
     * 授课课件创建人
     */
    private String userName;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 课程Name
     */
    private String unitName;

    /**
     * 授课课件年级
     */
    private Integer grade;

    /**
     * 授课课件科目
     */
    private String subjectId;

    /**
     * 授课课件科目
     */
    private String subjectName;

    /**
     * 授课课本
     */
    private String bookId;

    /**
     * 授课课本Name
     */
    private String bookName;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }

    public String getCoursewaresTitle() {
        return coursewaresTitle;
    }

    public void setCoursewaresTitle(String coursewaresTitle) {
        this.coursewaresTitle = coursewaresTitle;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TeachchooseDTO{" +
                "coursewaresId='" + coursewaresId + '\'' +
                ", coursewaresTitle='" + coursewaresTitle + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", grade=" + grade +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}