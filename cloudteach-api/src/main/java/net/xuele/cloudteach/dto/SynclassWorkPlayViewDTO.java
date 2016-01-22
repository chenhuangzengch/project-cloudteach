package net.xuele.cloudteach.dto;


import java.io.Serializable;

/**
 * SynclassWorkPlayViewDTO
 *
 * @author duzg
 * @date 2015/7/20 0002
 */
public class SynclassWorkPlayViewDTO extends SynclassWorkPlayDTO implements Serializable {

    private static final long serialVersionUID = 6025382552974351867L;
    /**
     * 游戏附件ID
     */
    private String gameFileId;
    /**
     * 游戏附件名称
     */
    private String gameName;
    /**
     * 真实游戏编号
     */
    private Integer cReal;

    public String getGameFileId() {
        return gameFileId;
    }

    public void setGameFileId(String gameFileId) {
        this.gameFileId = gameFileId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getcReal() {
        return cReal;
    }

    public void setcReal(Integer cReal) {
        this.cReal = cReal;
    }

    @Override
    public String toString() {
        return "SynclassWorkPlayViewDTO{" +
                "gameFileId='" + gameFileId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", cReal=" + cReal +
                '}';
    }
}