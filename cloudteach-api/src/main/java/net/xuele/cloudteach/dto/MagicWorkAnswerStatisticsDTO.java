package net.xuele.cloudteach.dto;

import java.io.Serializable;
/**
 * MagicWorkAnswerStatisticsDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkAnswerStatisticsDTO  implements Serializable {
    private static final long serialVersionUID = -2879732289101659393L;
    private String answerId;

    private Integer commentTimes;

    private Integer praiseTimes;

    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public Integer getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkAnswerStatisticsDTO{" +
                "answerId='" + answerId + '\'' +
                ", commentTimes=" + commentTimes +
                ", praiseTimes=" + praiseTimes +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}