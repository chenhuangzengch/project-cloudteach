package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkClassDTO implements Serializable {
    private static final long serialVersionUID = -170065991341615048L;
    /**
     * 教师作业班级ID
     */
    private String workClassId;

    /**
     * 作业ID（对应教师作业表主键）
     */
    private String workId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_CLASS] 的属性 教师作业班级ID
     */
    public String getWorkClassId() {
        return workClassId;
    }

    /**
     * 设置[CT_TEACHER_WORK_CLASS]的属性教师作业班级ID
     */
    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId == null ? null : workClassId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_CLASS] 的属性 作业ID（对应教师作业表主键）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_TEACHER_WORK_CLASS]的属性作业ID（对应教师作业表主键）
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_CLASS] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_TEACHER_WORK_CLASS]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_CLASS] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_CLASS]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_CLASS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_CLASS]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherWorkClassDTO{" +
                "workClassId='" + workClassId + '\'' +
                ", workId='" + workId + '\'' +
                ", classId='" + classId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}