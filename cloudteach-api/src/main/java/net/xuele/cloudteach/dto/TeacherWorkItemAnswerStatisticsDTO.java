package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkItemAnswerStatisticsDTO implements Serializable {
    private static final long serialVersionUID = -6822875697798701319L;
    /**
     * 教师作业题目学生回答ID（对应教师作业题目学生回答表主键）
     */
    private String answerId;

    /**
     * 评论次数
     */
    private Integer commentTimes;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS] 的属性 教师作业题目学生回答ID（对应教师作业题目学生回答表主键）
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS]的属性教师作业题目学生回答ID（对应教师作业题目学生回答表主键）
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS] 的属性 评论次数
     */
    public Integer getCommentTimes() {
        return commentTimes;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS]的属性评论次数
     */
    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS] 的属性 点赞次数
     */
    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS]的属性点赞次数
     */
    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_STATISTICS]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherWorkItemAnswerStatisticsDTO{" +
                "answerId='" + answerId + '\'' +
                ", commentTimes=" + commentTimes +
                ", praiseTimes=" + praiseTimes +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}