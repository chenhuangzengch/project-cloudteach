package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * ExerciseWorkAnswerCommentDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class ExerciseWorkAnswerCommentDTO implements Serializable {

    private static final long serialVersionUID = -8263966540934999955L;
    private String commentId;

    private String answerId;

    private String userType;

    private String userId;

    private String context;

    private Date commentTime;

    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

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

    @Override
    public String toString() {
        return "ExerciseWorkAnswerCommentDTO{" +
                "commentId='" + commentId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", userType='" + userType + '\'' +
                ", userId='" + userId + '\'' +
                ", context='" + context + '\'' +
                ", commentTime=" + commentTime +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}