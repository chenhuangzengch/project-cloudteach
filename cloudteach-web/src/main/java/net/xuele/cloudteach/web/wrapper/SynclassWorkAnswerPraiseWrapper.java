package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * SynclassWorkAnswerPraiseWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkAnswerPraiseWrapper {
    /**
     * 对应同步课堂作业学生表主键
     */
    private String workUserId;

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

    /**
     * 获取 [CT_SYNCLASS_WORK_ANSWER_PRAISE] 的属性 对应同步课堂作业学生表主键
     */
    public String getWorkUserId() {
        return workUserId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_ANSWER_PRAISE]的属性对应同步课堂作业学生表主键
     */
    public void setWorkUserId(String workUserId) {
        this.workUserId = workUserId == null ? null : workUserId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_ANSWER_PRAISE] 的属性 0学生，1老师
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_ANSWER_PRAISE]的属性0学生，1老师
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
     * 获取 [CT_SYNCLASS_WORK_ANSWER_PRAISE] 的属性 0未点赞，1点赞
     */
    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_ANSWER_PRAISE]的属性0未点赞，1点赞
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
}