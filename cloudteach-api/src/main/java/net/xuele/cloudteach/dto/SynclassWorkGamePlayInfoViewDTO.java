package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/29 0029.
 */
public class SynclassWorkGamePlayInfoViewDTO implements Serializable {
    private static final long serialVersionUID = -7673194973099301118L;

    private String playId;
    private int gameId;
    private int playTimes;
    private String gameName;
    private int maxScore;
    private String userId;

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SynclassWorkGamePlayInfoViewDTO{" +
                "gameId=" + gameId +
                ", playId='" + playId + '\'' +
                ", playTimes=" + playTimes +
                ", gameName='" + gameName + '\'' +
                ", maxScore=" + maxScore +
                ", userId='" + userId + '\'' +
                '}';
    }
}
