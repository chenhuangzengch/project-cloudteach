package net.xuele.cloudteach.web.wrapper;
/**
 * MagicWorkAnswerStatisticsWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkAnswerStatisticsWrapper {
    private String answerId;

    private Integer commentTimes;

    private Integer praiseTimes;

    private Integer status;

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
}