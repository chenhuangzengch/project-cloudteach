package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/12/3 0003.
 */
public class LearningInfoSynclassPlayGameDTO implements Serializable {
    private static final long serialVersionUID = -5059475188395941107L;

    /* 同步课堂游戏名称*/
    private String gameName;
    private List<LearningInfoStuSynclassWorkDTO> playInfo;
    private List<LearningInfoStuSynclassWorkDTO> notPlayInfo;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<LearningInfoStuSynclassWorkDTO> getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(List<LearningInfoStuSynclassWorkDTO> playInfo) {
        this.playInfo = playInfo;
    }

    public List<LearningInfoStuSynclassWorkDTO> getNotPlayInfo() {
        return notPlayInfo;
    }

    public void setNotPlayInfo(List<LearningInfoStuSynclassWorkDTO> notPlayInfo) {
        this.notPlayInfo = notPlayInfo;
    }
}
