package net.xuele.cloudteach.view;

import java.util.Date;

public class WorkAnswerPraiseView {
    /**
     * 作业学生回答ID
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
     * 点赞用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String icon;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
}