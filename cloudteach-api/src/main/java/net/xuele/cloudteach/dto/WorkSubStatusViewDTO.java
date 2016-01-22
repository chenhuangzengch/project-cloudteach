package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/9/22 0022.
 */
public class WorkSubStatusViewDTO implements Serializable {

    private static final long serialVersionUID = 5687762835126837963L;
    // 作业ID
    private String workId;
    // 提交状态
    private int subStatus;
    // 教师评分
    private int score;
    // 系统评分
    private int sysScore;

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSysScore() {
        return sysScore;
    }

    public void setSysScore(int sysScore) {
        this.sysScore = sysScore;
    }

    @Override
    public String toString() {
        return "WorkSubStatusViewDTO{" +
                "score=" + score +
                ", workId='" + workId + '\'' +
                ", subStatus=" + subStatus +
                ", sysScore=" + sysScore +
                '}';
    }
}
