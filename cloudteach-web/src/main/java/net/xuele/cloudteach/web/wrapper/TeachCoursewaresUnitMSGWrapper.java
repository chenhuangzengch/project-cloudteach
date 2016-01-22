package net.xuele.cloudteach.web.wrapper;

public class TeachCoursewaresUnitMSGWrapper {
    /**
     * 课程名
     */
    private String unitName;
    /**
     * 科目
     */
    private String subjectId;

    /**
     * 科目名称（冗余字段）
     */
    private String subjectName;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 学期
     */
    private Integer semester;

    /**
     * 学期描述
     */
    private String semesterDescribe;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSemesterDescribe() {
        return semesterDescribe;
    }

    public void setSemesterDescribe(String semesterDescribe) {
        this.semesterDescribe = semesterDescribe;
    }
}