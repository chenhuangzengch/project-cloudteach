package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.LearningInfoStuTeacherWorkDTO;

import java.util.List;

/**
 * Created by hujx on 2015/12/8 0008.
 */
public class LearningInfoVoiceWorkWrapper {

    private String workId;
    private String context;
    private int avgPlayFreq;
    private int avgScore;
    private LearningInfoSubmitStatisWrapper partakeRate;
    private List<LearningInfoStuTeacherWorkDTO> submitStuWorkInfo;
    private LearningInfoScoreLevelWrapper levelStuNum;

    public int getAvgPlayFreq() {
        return avgPlayFreq;
    }

    public void setAvgPlayFreq(int avgPlayFreq) {
        this.avgPlayFreq = avgPlayFreq;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LearningInfoScoreLevelWrapper getLevelStuNum() {
        return levelStuNum;
    }

    public void setLevelStuNum(LearningInfoScoreLevelWrapper levelStuNum) {
        this.levelStuNum = levelStuNum;
    }

    public LearningInfoSubmitStatisWrapper getPartakeRate() {
        return partakeRate;
    }

    public void setPartakeRate(LearningInfoSubmitStatisWrapper partakeRate) {
        this.partakeRate = partakeRate;
    }

    public List<LearningInfoStuTeacherWorkDTO> getSubmitStuWorkInfo() {
        return submitStuWorkInfo;
    }

    public void setSubmitStuWorkInfo(List<LearningInfoStuTeacherWorkDTO> submitStuWorkInfo) {
        this.submitStuWorkInfo = submitStuWorkInfo;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
