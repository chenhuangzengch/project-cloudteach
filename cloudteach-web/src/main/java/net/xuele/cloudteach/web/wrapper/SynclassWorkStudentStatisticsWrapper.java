package net.xuele.cloudteach.web.wrapper;
/**
 * SynclassWorkStudentStatisticsWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkStudentStatisticsWrapper {
    /**
     * 对应同步课堂作业学生表主键
     */
    private String workUserId;

    private Integer commentTimes;

    private Integer praiseTimes;

    private Integer status;

    /**
     * 获取 [CT_SYNCLASS_WORK_STUDENT_STATISTICS] 的属性 对应同步课堂作业学生表主键
     */
    public String getWorkUserId() {
        return workUserId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_STUDENT_STATISTICS]的属性对应同步课堂作业学生表主键
     */
    public void setWorkUserId(String workUserId) {
        this.workUserId = workUserId == null ? null : workUserId.trim();
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