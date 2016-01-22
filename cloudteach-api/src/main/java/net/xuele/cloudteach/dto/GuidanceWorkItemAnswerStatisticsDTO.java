package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * GuidanceWorkItemAnswerStatisticsDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkItemAnswerStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 3824201669282128207L;
    /**
     * 预习作业学生回答ID
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
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS] 的属性 预习作业学生回答ID
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS]的属性预习作业学生回答ID
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS] 的属性 评论次数
     */
    public Integer getCommentTimes() {
        return commentTimes;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS]的属性评论次数
     */
    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS] 的属性 点赞次数
     */
    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS]的属性点赞次数
     */
    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM_ANSWER_STATISTICS]的属性状态
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
        return "GuidanceWorkItemAnswerStatisticsDTO{" +
                "answerId='" + answerId + '\'' +
                ", commentTimes=" + commentTimes +
                ", praiseTimes=" + praiseTimes +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}