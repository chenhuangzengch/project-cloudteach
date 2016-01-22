package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * Created by hujx on 2015/8/5 0005.
 */
public class MagicWorkChallengeHistoryWrapper {

    /**
     * 作业开始时间
     */
    private Date beginTime;

    /**
     * 作业完成时间
     */
    private Date endTime;

    /**
     * 作业分数
     */
    private Integer score;

    /**
     * 挑战成绩描述
     */
    private String scorecontext;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScorecontext() {
        return scorecontext;
    }

    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext;
    }
}
