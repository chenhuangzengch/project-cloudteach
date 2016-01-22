package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtSynclassWorkGame;

/**
 * SynclassWorkGameView
 *
 * @author duzg
 * @date 2015/7/21 0002
 */
public class SynclassWorkGameView extends CtSynclassWorkGame {
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

    /**
     * 游戏课件游戏编号
     */
    private Integer cId;

    private String ckName;

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

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getCkName() {
        return ckName;
    }

    public void setCkName(String ckName) {
        this.ckName = ckName;
    }
}