package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * SynclassWorkPlayWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkPlayWrapper {
    private String playId;

    /**
     * 对应同步课堂作业表主键
     */
    private String workId;

    /**
     * 对应同步课堂作业游戏附件表主键
     */
    private String gameId;

    private String userId;

    private Integer playTimes;

    private Integer maxScore;

    /**
     * 0未提交，1已提交
     */
    private Integer finishStatus;

    private Date subTime;

    private Date updateTime;

    private String updateUserId;

    private Integer status;

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId == null ? null : playId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_PLAY] 的属性 对应同步课堂作业表主键
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_PLAY]的属性对应同步课堂作业表主键
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_PLAY] 的属性 对应同步课堂作业游戏附件表主键
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_PLAY]的属性对应同步课堂作业游戏附件表主键
     */
    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_PLAY] 的属性 0未提交，1已提交
     */
    public Integer getFinishStatus() {
        return finishStatus;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_PLAY]的属性0未提交，1已提交
     */
    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}