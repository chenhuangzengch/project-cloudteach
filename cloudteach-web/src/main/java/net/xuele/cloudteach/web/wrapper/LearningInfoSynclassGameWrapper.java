package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.LearningInfoStuSynclassWorkDTO;
import net.xuele.cloudteach.dto.LearningInfoSynclassPlayGameDTO;

import java.util.List;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情同步课堂游戏
 */
public class LearningInfoSynclassGameWrapper {

    private String gameName;
    private int avgPlayFreq;
    private int avgScore;
    private int avgPlayTime;
    private List<LearningInfoStuSynclassWorkDTO> submitStuSynclassInfo;
    private LearningInfoSubmitStatisWrapper partakeRate;
    private LearningInfoScoreLevelWrapper levelStuNum;

    public int getAvgPlayFreq() {
        return avgPlayFreq;
    }

    public void setAvgPlayFreq(int avgPlayFreq) {
        this.avgPlayFreq = avgPlayFreq;
    }

    public int getAvgPlayTime() {
        return avgPlayTime;
    }

    public void setAvgPlayTime(int avgPlayTime) {
        this.avgPlayTime = avgPlayTime;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public List<LearningInfoStuSynclassWorkDTO> getSubmitStuSynclassInfo() {
        return submitStuSynclassInfo;
    }

    public void setSubmitStuSynclassInfo(List<LearningInfoStuSynclassWorkDTO> submitStuSynclassInfo) {
        this.submitStuSynclassInfo = submitStuSynclassInfo;
    }

}
