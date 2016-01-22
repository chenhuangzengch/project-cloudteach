package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkDTO implements Serializable {


    private static final long serialVersionUID = 1355063131700771256L;
    /**
     * 作业ID
     */
    private String workId;

    /**
     * 发布教师ID
     */
    private String userId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 题目类型：1预习 4电子作业 7口语作业
     */
    private Integer workType;

    /**
     * 发布类型：0定时发布，1及时发布
     */
    private Integer publishType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 作业最晚提交日期
     */
    private Date endTime;

    /**
     * 作业完成状态
     */
    private Integer finishStatus;

    /**
     * 作业批改状态
     */
    private Integer correctStatus;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 发布教师ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性发布教师ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 学校ID
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性学校ID
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 题目类型：1预习 4电子作业 7口语作业
     */
    public Integer getWorkType() {
        return workType;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性题目类型：1预习 4电子作业 7口语作业
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 发布类型：0定时发布，1及时发布
     */
    public Integer getPublishType() {
        return publishType;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性发布类型：0定时发布，1及时发布
     */
    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 最后修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性最后修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 作业最晚提交日期
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性作业最晚提交日期
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 作业完成状态
     */
    public Integer getFinishStatus() {
        return finishStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性作业完成状态
     */
    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 作业批改状态
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性作业批改状态
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    /**
     * 获取 [CT_TEACHER_WORK] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherWorkDTO{" +
                "workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", workType=" + workType +
                ", publishType=" + publishType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", publishTime=" + publishTime +
                ", endTime=" + endTime +
                ", finishStatus=" + finishStatus +
                ", correctStatus=" + correctStatus +
                ", status=" + status +
                '}';
    }
}
