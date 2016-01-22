package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.dto.SynclassWorkDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by cm.wang on 2015/7/24 0024.
 */
public class SynclassWorkForm {

    private String workId;

    private String unitId;

    private String context;

    private Long publishTime;

    private String classJson;

    /**
     * 班级列表
     */
    private List<String> classList;

    /**
     * 游戏附件列表
     */
    private List<Integer> gameList;

    /**
     * 最晚提交时间
     */
    private int lastCommitDay;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    public List<Integer> getGameList() {
        return gameList;
    }

    public void setGameList(List<Integer> gameList) {
        this.gameList = gameList;
    }

    public int getLastCommitDay() {
        return lastCommitDay;
    }

    public void setLastCommitDay(int lastCommitDay) {
        this.lastCommitDay = lastCommitDay;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }

    @Override
    public String toString() {
        return "SynclassWorkForm{" +
                "workId='" + workId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", context='" + context + '\'' +
                ", publishTime=" + publishTime +
                ", classJson='" + classJson + '\'' +
                ", classList=" + classList +
                ", gameList=" + gameList +
                ", lastCommitDay=" + lastCommitDay +
                '}';
    }
}
