package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * GuidanceWorkStudentDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkStudentDTO implements Serializable {
    private static final long serialVersionUID = 1424611633660385428L;

    /**
     *
     */
    private String workUserId;

    /**
     * 预习作业学生ID
     */
    private String workId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 班级ID
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
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    public String getWorkUserId() {
        return workUserId;
    }

    public void setWorkUserId(String workUserId) {
        this.workUserId = workUserId;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_STUDENT] 的属性 预习作业学生ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_STUDENT]的属性预习作业学生ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_STUDENT] 的属性 学生用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_STUDENT]的属性学生用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_STUDENT] 的属性 提交状态
     */
    public Integer getSubStatus() {
        return subStatus;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_STUDENT]的属性提交状态
     */
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_STUDENT] 的属性 批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_STUDENT]的属性批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_STUDENT] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_STUDENT]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GuidanceWorkStudentDTO{" +
                "workUserId='" + workUserId + '\'' +
                ", workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", classId='" + classId + '\'' +
                ", subStatus=" + subStatus +
                ", correctStatus=" + correctStatus +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}