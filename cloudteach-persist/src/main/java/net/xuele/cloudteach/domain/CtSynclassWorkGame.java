package net.xuele.cloudteach.domain;
/**
 * CtSynclassWorkGame
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class CtSynclassWorkGame {
    /**
     * 同步课堂作业游戏附件ID
     */
    private String workGameId;

    /**
     * 同步课堂作业ID
     */
    private String workId;

    /**
     * 游戏附件ID
     */
    private Integer gameId;

    /**
     * 状态
     */
    private Integer status;

    private  String schoolId;

    /**
     * 获取 [CT_SYNCLASS_WORK_GAME] 的属性 同步课堂作业游戏附件ID
     */
    public String getWorkGameId() {
        return workGameId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_GAME]的属性同步课堂作业游戏附件ID
     */
    public void setWorkGameId(String workGameId) {
        this.workGameId = workGameId == null ? null : workGameId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_GAME] 的属性 同步课堂作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_GAME]的属性同步课堂作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_GAME] 的属性 游戏附件ID
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_GAME]的属性游戏附件ID
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId ;
    }

    /**
     * 获取 [CT_SYNCLASS_WORK_GAME] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_SYNCLASS_WORK_GAME]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}