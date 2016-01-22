package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * SynclassWorkAnswerCommentDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkAnswerCommentDTO implements Serializable {
    private static final long serialVersionUID = 8390852288568931666L;
    private String commentId;

    /**
     * 对应同步课堂作业学生表主键
     */
    private String workUserId;

    private String userId;

    private String userType;

    private String context;

    private Date commentTime;

    private Integer status;

    private  String schoolId;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_ANSWER_COMMENT] 的属性 对应同步课堂作业学生表主键
     */
    public String getWorkUserId() {
        return workUserId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_ANSWER_COMMENT]的属性对应同步课堂作业学生表主键
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getStatus() {
        return status;
    }

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
        return "SynclassWorkAnswerCommentDTO{" +
                "commentId='" + commentId + '\'' +
                ", workUserId='" + workUserId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", context='" + context + '\'' +
                ", commentTime=" + commentTime +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}