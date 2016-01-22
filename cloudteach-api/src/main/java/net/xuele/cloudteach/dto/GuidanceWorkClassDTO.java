package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * GuidanceWorkClassDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkClassDTO implements Serializable {

    private static final long serialVersionUID = -3739650553660073618L;

    /**
     * 主键
     */
    private String workClassId;
    /**
     * 预习作业ID
     */
    private String workId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_CLASS] 的属性 预习作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_CLASS]的属性预习作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_CLASS] 的属性 班级ID
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_CLASS]的属性班级ID
     */
    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_CLASS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_CLASS]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GuidanceWorkClassDTO{" +
                "workClassId='" + workClassId + '\'' +
                ", workId='" + workId + '\'' +
                ", classId='" + classId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}