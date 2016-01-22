package net.xuele.cloudteach.view;

import java.io.Serializable;

/**
 * Created by hujx on 2015/8/8 0008.
 */
public class SynclassWorkGameInfoView {

    // 游戏ID(UC_ID)
    private int gameId;

    // 游戏playId
    private String playId;

    // 玩游戏次数
    private int playTimes;

    // 游戏成绩
    private int maxScore;

    // 游戏名称(C_Title)
    private String gameName;

    // 真实游戏编号(C_Real)
    private int CReal;

    public int getCReal() {
        return CReal;
    }

    public void setCReal(int CReal) {
        this.CReal = CReal;
    }

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

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }
}
