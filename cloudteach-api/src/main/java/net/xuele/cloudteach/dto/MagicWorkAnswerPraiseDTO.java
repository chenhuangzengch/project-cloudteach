package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * MagicWorkAnswerPraiseDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkAnswerPraiseDTO implements Serializable {
    private static final long serialVersionUID = 1426704721258157660L;

    private String praiseId;

    private String answerId;

    private String userId;

    /**
     * 0学生，1老师
     */
    private Integer userType;

    private Date praiseTime;

    /**
     * 0未点赞，1点赞
     */
    private Integer praiseStatus;

    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(String praiseId) {
        this.praiseId = praiseId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_PRAISE] 的属性 0学生，1老师
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_PRAISE]的属性0学生，1老师
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    /**
     * 获取 [CT_MAGIC_WORK_ANSWER_PRAISE] 的属性 0未点赞，1点赞
     */
    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK_ANSWER_PRAISE]的属性0未点赞，1点赞
     */
    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkAnswerPraiseDTO{" +
                "praiseId='" + praiseId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType=" + userType +
                ", praiseTime=" + praiseTime +
                ", praiseStatus=" + praiseStatus +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}