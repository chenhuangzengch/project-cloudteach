package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.LearningInfoStuSynclassWorkDTO;

import java.util.List;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情同步课堂
 */
public class LearningInfoSynclassWorkWrapper {

    private String workId;
    private String context;
    private List<LearningInfoSynclassGameWrapper> gamePlayinfo;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<LearningInfoSynclassGameWrapper> getGamePlayinfo() {
        return gamePlayinfo;
    }

    public void setGamePlayinfo(List<LearningInfoSynclassGameWrapper> gamePlayinfo) {
        this.gamePlayinfo = gamePlayinfo;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
