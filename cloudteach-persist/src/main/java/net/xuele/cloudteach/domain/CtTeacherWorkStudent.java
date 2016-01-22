package net.xuele.cloudteach.domain;

public class CtTeacherWorkStudent {
    /**
     * 教师作业学生ID
     */
    private String workUserId;

    /**
     * 作业ID（对应教师作业表主键）
     */
    private String workId;

    /**
     * 学生ID
     */
    private String userId;

    /**
     * 学生班级ID
     */
    private String classId;

    /**
     * 提交状态
     */
    private Integer subStatus;

    /**
     * 批改状态
     */
    private Integer correctStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 教师作业学生ID
     */
    public String getWorkUserId() {
        return workUserId;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性教师作业学生ID
     */
    public void setWorkUserId(String workUserId) {
        this.workUserId = workUserId == null ? null : workUserId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 作业ID（对应教师作业表主键）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性作业ID（对应教师作业表主键）
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 学生ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性学生ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 学生班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性学生班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 提交状态
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性提交状态
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_STUDENT] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_STUDENT]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}