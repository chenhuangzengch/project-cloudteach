package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.LearningInfoStuTeacherWorkDTO;

import java.util.List;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情电子作业
 */
public class LearningInfoExerciseWorkWrapper {

    private String workId;
    private String context;
    private long avgWorkTime;
    private LearningInfoSubmitStatisWrapper partakeRate;
    private List<LearningInfoStuTeacherWorkDTO> submitStuWorkInfo;
    private LearningInfoScoreLevelWrapper levelStuNum;

    public long getAvgWorkTime() {
        return avgWorkTime;
    }

    public void setAvgWorkTime(long avgWorkTime) {
        this.avgWorkTime = avgWorkTime;
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
