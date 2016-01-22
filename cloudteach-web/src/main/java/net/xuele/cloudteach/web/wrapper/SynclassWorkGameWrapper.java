package net.xuele.cloudteach.web.wrapper;
/**
 * SynclassWorkGameWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkGameWrapper {
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
     * 游戏附件ID
     */
    private Integer gameFileId;
    /**
     * 游戏附件名称
     */
    private String gameName;

    /**
     * 游戏类型名称
     */
    private String ckName;

    /**
     * 游戏附件图片路径
     */
    private  String gamePicUrl;

    /**
     * 游戏路径
     */
    private String gameUrl;
    /**
     * 真实游戏编号
     */
    private Integer cReal;

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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameFileId() {
        return gameFileId;
    }

    public void setGameFileId(Integer gameFileId) {
        this.gameFileId = gameFileId;
    }

    public Integer getcReal() {
        return cReal;
    }

    public void setcReal(Integer cReal) {
        this.cReal = cReal;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePicUrl() {
        return gamePicUrl;
    }

    public void setGamePicUrl(String gamePicUrl) {
        this.gamePicUrl = gamePicUrl;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getCkName() {
        return ckName;
    }

    public void setCkName(String ckName) {
        this.ckName = ckName;
    }
}