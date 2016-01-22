package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/8/8 0008.
 */
public class SynclassWorkGameInfoViewDTO implements Serializable {

    private static final long serialVersionUID = 4572168644152955229L;

    // 游戏ID(UC_ID)
    private int gameId;

    // 游戏playId
    private String playId;

    // 玩游戏次数
    private int playTimes;

    // 游戏成绩百分比
    private int percentScore;

    // 游戏成绩页面显示(x.x分)
    private float floatScore;

    // 游戏名称(C_Title)
    private String gameName;

    // 真实游戏编号(C_Real)
    private int CReal;

    // 游戏图片URL用
    private int CID;

    // 游戏回传信息
    private String UID;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getCReal() {
        return CReal;
    }

    public void setCReal(int CReal) {
        this.CReal = CReal;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public float getFloatScore() {
        return floatScore;
    }

    public void setFloatScore(float floatScore) {
        this.floatScore = floatScore;
    }

    public int getPercentScore() {
        return percentScore;
    }

    public void setPercentScore(int percentScore) {
        this.percentScore = percentScore;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    @Override
    public String toString() {
        return "SynclassWorkGameInfoViewDTO{" +
                "CID=" + CID +
                ", gameId=" + gameId +
                ", playId='" + playId + '\'' +
                ", playTimes=" + playTimes +
                ", percentScore=" + percentScore +
                ", floatScore=" + floatScore +
                ", gameName='" + gameName + '\'' +
                ", CReal=" + CReal +
                ", UID='" + UID + '\'' +
                '}';
    }
}
