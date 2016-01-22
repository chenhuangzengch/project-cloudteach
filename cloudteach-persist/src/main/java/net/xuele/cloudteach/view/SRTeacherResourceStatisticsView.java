package net.xuele.cloudteach.view;


/**
 * SRTeacherResourceStatisticsView
 * 区划下教师资源统计
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRTeacherResourceStatisticsView {



    /**
     * 资源数量
     */
    private Integer resourceAmount;

    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 所在学校ID
     */
    private String schoolId;

    /**
     * 所在学校
     */
    private String schoolName;

    /**
     * 任课科目
     */
    private String subjectName;

    /**
     * 职务
     */
    private String dutyName;

    public Integer getResourceAmount() {
        return resourceAmount;
    }

    public void setResourceAmount(Integer resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public SRTeacherResourceStatisticsView() {
    }
}
