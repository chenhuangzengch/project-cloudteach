package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/12/8 0008.
 */
public class LearningInfoWorkDTO<T> implements Serializable {
    private static final long serialVersionUID = 1633910532953920355L;

    private String workId;
    /* 作业描述 */
    private String context;
    /* 完成作业学生信息 */
    private T FinishedInfo;
    /* 未完成作业学生信息 */
    private T NotFinishedInfo;
    /* 同步课堂信息 */
    private LearningInfoSynclassPlayGameDTO playGameInfo;
    /* 作业评分等级 */
    private LearningInfoScoreLevelDTO scoreLevel;
    /* 作业平均统计 */
    private LearningInfoAverageDTO average;

    public LearningInfoSynclassPlayGameDTO getPlayGameInfo() {
        return playGameInfo;
    }

    public void setPlayGameInfo(LearningInfoSynclassPlayGameDTO playGameInfo) {
        this.playGameInfo = playGameInfo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public T getFinishedInfo() {
        return FinishedInfo;
    }

    public void setFinishedInfo(T finishedInfo) {
        FinishedInfo = finishedInfo;
    }

    public T getNotFinishedInfo() {
        return NotFinishedInfo;
    }

    public void setNotFinishedInfo(T notFinishedInfo) {
        NotFinishedInfo = notFinishedInfo;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public LearningInfoAverageDTO getAverage() {
        return average;
    }

    public void setAverage(LearningInfoAverageDTO average) {
        this.average = average;
    }

    public LearningInfoScoreLevelDTO getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(LearningInfoScoreLevelDTO scoreLevel) {
        this.scoreLevel = scoreLevel;
    }
}
