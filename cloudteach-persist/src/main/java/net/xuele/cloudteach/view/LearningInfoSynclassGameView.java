package net.xuele.cloudteach.view;

import java.io.Serializable;

/**
 * Created by hujx on 2015/12/3 0003.
 */
public class LearningInfoSynclassGameView {

    /* 游戏ID*/
    private Integer gameId;
    /* 同步课堂游戏作业游戏ID*/
    private String workGameId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getWorkGameId() {
        return workGameId;
    }

    public void setWorkGameId(String workGameId) {
        this.workGameId = workGameId;
    }

}
