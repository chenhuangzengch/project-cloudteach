package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkAnswerPraiseViewDTO
 * 作业回答点赞记录VIEW
 * @author duzg
 * @date 2015/7/17 0002
 */
public class WorkAnswerPraiseViewDTO implements Serializable {
    private static final long serialVersionUID = 5245209352250467855L;
    /**
     * 点赞用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    /**
     * 预习作业学生回答ID
     */
    private String answerId;

    /**
     * 点赞用户ID
     */
    private String userId;

    /**
     * 用户类型（0,学生，1老师）
     */
    private Integer userType;

    /**
     * 点赞时间
     */
    private Date praiseTime;

    /**
     * 用户点赞状态（0，未点赞，1点赞）
     */
    private Integer praiseStatus;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 预习作业学生回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性预习作业学生回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 点赞用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性点赞用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 用户类型（0,学生，1老师）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性用户类型（0,学生，1老师）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 点赞时间
     */
    public Date getPraiseTime() {
        return praiseTime;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性点赞时间
     */
    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 用户点赞状态（0，未点赞，1点赞）
     */
    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性用户点赞状态（0，未点赞，1点赞）
     */
    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_PRAISE]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    private String icon;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "WorkAnswerPraiseViewDTO{" +
                "userName='" + userName + '\'' +
                ", answerId='" + answerId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType=" + userType +
                ", praiseTime=" + praiseTime +
                ", praiseStatus=" + praiseStatus +
                ", status=" + status +
                ", icon='" + icon + '\'' +
                '}';
    }
}