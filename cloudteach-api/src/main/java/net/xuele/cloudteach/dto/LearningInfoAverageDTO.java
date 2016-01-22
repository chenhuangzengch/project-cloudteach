package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情作业，平均计算DTO
 */
public class LearningInfoAverageDTO<T> implements Serializable {
    private static final long serialVersionUID = -3211419505275119781L;

    /* 平均作业时间 */
    private T AvgWorkTime;
    /* 平均得分 */
    private T AvgScore;
    /* 平均练习次数 */
    private T AvgPlayFreq;
    /* 平均练习时间 */
    private T AvgPlayTime;

    public T getAvgPlayFreq() {
        return AvgPlayFreq;
    }

    public void setAvgPlayFreq(T avgPlayFreq) {
        AvgPlayFreq = avgPlayFreq;
    }

    public T getAvgPlayTime() {
        return AvgPlayTime;
    }

    public void setAvgPlayTime(T avgPlayTime) {
        AvgPlayTime = avgPlayTime;
    }

    public T getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(T avgScore) {
        AvgScore = avgScore;
    }

    public T getAvgWorkTime() {
        return AvgWorkTime;
    }

    public void setAvgWorkTime(T avgWorkTime) {
        AvgWorkTime = avgWorkTime;
    }

    @Override
    public String toString() {
        return "LearningInfoAverageDTO{" +
                "AvgPlayFreq=" + AvgPlayFreq +
                ", AvgWorkTime=" + AvgWorkTime +
                ", AvgScore=" + AvgScore +
                ", AvgPlayTime=" + AvgPlayTime +
                '}';
    }
}
